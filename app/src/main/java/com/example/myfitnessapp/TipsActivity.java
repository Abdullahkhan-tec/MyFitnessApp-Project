package com.example.myfitnessapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class TipsActivity extends AppCompatActivity {

    private TextView tvTips;
    private String cachedTips = null;
    private static final long API_DELAY_MS = 1000;
    private AIHelper aiHelper;
    private String bmiCategory = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);

        tvTips = findViewById(R.id.tvTips);
        aiHelper = AIHelper.getInstance();

        // Get BMI from intent
        if (getIntent() != null && getIntent().hasExtra("BMI_CATEGORY")) {
            bmiCategory = getIntent().getStringExtra("BMI_CATEGORY");
        }

        getTipsWithDelay();
    }

    private void getTipsWithDelay() {
        if (cachedTips != null) {
            tvTips.setText(cachedTips);
            return;
        }

        new Handler(Looper.getMainLooper()).postDelayed(this::callAIForTips, API_DELAY_MS);
    }

    private void callAIForTips() {
        String prompt = "Give 5 short fitness and nutrition tips";
        if (bmiCategory != null && !bmiCategory.isEmpty()) {
            prompt += " for a person with BMI category " + bmiCategory;
        }

        tvTips.setText("Fetching tips from AI...");

        aiHelper.getAIResponse(prompt, new AIHelper.AIResponseCallback() {
            @Override
            public void onResponse(String response) {
                cachedTips = response;
                tvTips.setText(cachedTips);
            }

            @Override
            public void onError(String error) {
                tvTips.setText(
                        "AI unavailable. Stay hydrated, eat balanced meals, exercise regularly, sleep well."
                );
            }
        });
    }
}
