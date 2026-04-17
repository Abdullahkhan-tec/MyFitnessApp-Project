package com.example.myfitnessapp;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.JsonObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AIHelper {

    private static final String TAG = "AIHelper";

    private static AIHelper instance;
    // Emulator: use 10.0.2.2 to reach localhost Flask server
    private static final String API_URL = "http://10.0.2.2:5000/ai-tip";
    private OkHttpClient client;

    private String lastResponse = null;
    private long lastCallTime = 0;
    private static final long MIN_INTERVAL = 1000; // 1 second cache

    public interface AIResponseCallback {
        void onResponse(String response);
        void onError(String error);
    }

    private AIHelper() {
        client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .build();
    }

    public static AIHelper getInstance() {
        if (instance == null) instance = new AIHelper();
        return instance;
    }

    public void getAIResponse(String prompt, AIResponseCallback callback) {
        long now = System.currentTimeMillis();

        // Return cached response if called too quickly
        if (lastResponse != null && (now - lastCallTime) < MIN_INTERVAL) {
            new Handler(Looper.getMainLooper()).post(() ->
                    callback.onResponse(lastResponse));
            return;
        }

        lastCallTime = now;

        new Thread(() -> {
            try {
                JsonObject json = new JsonObject();
                json.addProperty("prompt", prompt);

                RequestBody body = RequestBody.create(
                        json.toString(),
                        MediaType.parse("application/json; charset=utf-8")
                );

                Request request = new Request.Builder()
                        .url(API_URL)
                        .post(body)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e(TAG, "AI call failed", e);
                        new Handler(Looper.getMainLooper()).post(() ->
                                callback.onError("AI unavailable: " + e.getMessage()));
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String bodyStr = response.body() != null ? response.body().string() : "";
                        Log.d(TAG, "AI Response body: " + bodyStr);

                        if (!response.isSuccessful()) {
                            new Handler(Looper.getMainLooper()).post(() ->
                                    callback.onError("HTTP Error: " + response.code() + " " + bodyStr));
                            return;
                        }

                        String aiText = parseAIResponse(bodyStr);
                        lastResponse = aiText;

                        new Handler(Looper.getMainLooper()).post(() ->
                                callback.onResponse(aiText));
                    }
                });

            } catch (Exception e) {
                Log.e(TAG, "AI exception", e);
                new Handler(Looper.getMainLooper()).post(() ->
                        callback.onError("AI exception: " + e.getMessage()));
            }
        }).start();
    }

    private String parseAIResponse(String json) {
        try {
            com.google.gson.JsonObject obj =
                    com.google.gson.JsonParser.parseString(json).getAsJsonObject();
            if (obj.has("response")) {
                return obj.get("response").getAsString();
            } else if (obj.has("error")) {
                return "AI Error: " + obj.get("error").getAsString();
            } else {
                return "No AI response received";
            }
        } catch (Exception e) {
            Log.e(TAG, "Parsing AI response failed", e);
            return "AI parse failed: " + e.getMessage();
        }
    }
}
