package com.example.myfitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MealActivity extends AppCompatActivity {

    LinearLayout cardGain, cardLoss, cardMaintain;
    TextView tvAIMealTip;
    AIHelper aiHelper;
    private String bmiCategory = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);

        cardGain = findViewById(R.id.cardGain);
        cardLoss = findViewById(R.id.cardLoss);
        cardMaintain = findViewById(R.id.cardMaintain);
        tvAIMealTip = findViewById(R.id.tvAIMealTip);

        aiHelper = AIHelper.getInstance();

        if (getIntent() != null && getIntent().hasExtra("BMI_CATEGORY")) {
            bmiCategory = getIntent().getStringExtra("BMI_CATEGORY");
        }

        if (bmiCategory == null || bmiCategory.isEmpty()) {
            Toast.makeText(this, "Please calculate BMI first", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Load AI meal tips
        loadMealTips(bmiCategory);

        // Hide all cards initially
        cardGain.setVisibility(View.GONE);
        cardLoss.setVisibility(View.GONE);
        cardMaintain.setVisibility(View.GONE);

        // Show the correct card based on BMI
        switch (bmiCategory.toUpperCase()) {
            case "UNDERWEIGHT":
                cardGain.setVisibility(View.VISIBLE);
                cardGain.setOnClickListener(v -> openMealDetail("GAIN"));
                break;
            case "NORMAL":
                cardMaintain.setVisibility(View.VISIBLE);
                cardMaintain.setOnClickListener(v -> openMealDetail("MAINTAIN"));
                break;
            case "OVERWEIGHT":
            case "OBESE":
                cardLoss.setVisibility(View.VISIBLE);
                cardLoss.setOnClickListener(v -> openMealDetail("LOSS"));
                break;
            default:
                cardMaintain.setVisibility(View.VISIBLE);
                cardMaintain.setOnClickListener(v -> openMealDetail("MAINTAIN"));
                break;
        }
    }

    private void loadMealTips(String bmiCategory) {
        tvAIMealTip.setText("Fetching meal tips from AI...");
        String prompt = "Give a short healthy meal suggestion for a person with BMI category " + bmiCategory;
        aiHelper.getAIResponse(prompt, new AIHelper.AIResponseCallback() {
            @Override
            public void onResponse(String response) {
                tvAIMealTip.setText(response);
            }

            @Override
            public void onError(String error) {
                tvAIMealTip.setText("AI unavailable. Eat balanced meals!");
            }
        });
    }

    private void openMealDetail(String type) {
        Intent intent = new Intent(this, MealDetailActivity.class);
        intent.putExtra("MEAL_TYPE", type);
        intent.putExtra("BMI_CATEGORY", bmiCategory); // pass BMI if needed for AI tips
        startActivity(intent);
    }
}
