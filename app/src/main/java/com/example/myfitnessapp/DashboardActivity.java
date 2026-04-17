package com.example.myfitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    LinearLayout bmiCard, workoutCard, mealCard, progressCard, tipsCard,
            supplementCard, muscleScienceCard, supplementSafetyCard;

    TextView tvAITip;
    String bmiCategory;
    AIHelper aiHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Bind views
        bmiCard = findViewById(R.id.cardBMI);
        workoutCard = findViewById(R.id.cardWorkout);
        mealCard = findViewById(R.id.cardMeal);
        progressCard = findViewById(R.id.cardProgress);
        tipsCard = findViewById(R.id.cardTips);
        supplementCard = findViewById(R.id.cardSupplements);
        muscleScienceCard = findViewById(R.id.cardMuscleScience);
        supplementSafetyCard = findViewById(R.id.cardSupplementSafety);
        tvAITip = findViewById(R.id.tvAITip);

        // Get BMI category safely
        bmiCategory = getIntent().getStringExtra("BMI_CATEGORY");
        if (bmiCategory == null) bmiCategory = "";

        // Load AI Tip
        aiHelper = AIHelper.getInstance();
        loadAITip(bmiCategory);

        // --- Click Listeners ---
        bmiCard.setOnClickListener(v ->
                startActivity(new Intent(this, BmiActivity.class))
        );
        workoutCard.setOnClickListener(v -> openActivityWithBMI(WorkoutActivity.class));
        mealCard.setOnClickListener(v -> openActivityWithBMI(MealActivity.class));
        supplementCard.setOnClickListener(v -> {
            Intent intent = new Intent(this, SupplementActivity.class);
            intent.putExtra("BMI_CATEGORY", bmiCategory);
            startActivity(intent);
        });
        supplementSafetyCard.setOnClickListener(v ->
                startActivity(new Intent(this, SupplementInfoActivity.class))
        );
        muscleScienceCard.setOnClickListener(v ->
                startActivity(new Intent(this, MuscleScienceActivity.class))
        );
        progressCard.setOnClickListener(v ->
                startActivity(new Intent(this, ProgressTrackerActivity.class))
        );
        tipsCard.setOnClickListener(v -> {
            Intent intent = new Intent(this, TipsActivity.class);
            intent.putExtra("BMI_CATEGORY", bmiCategory);
            startActivity(intent);
        });
    }

    // --- Helper Methods ---
    private void loadAITip(String bmiCategory) {
        if (bmiCategory.isEmpty()) {
            tvAITip.setText("Stay active & eat healthy!");
            return;
        }

        tvAITip.setText("Fetching AI tip...");
        String prompt = "Give a short fitness tip for a person with BMI category " + bmiCategory;
        aiHelper.getAIResponse(prompt, new AIHelper.AIResponseCallback() {
            @Override
            public void onResponse(String response) {
                tvAITip.setText(response);
            }

            @Override
            public void onError(String error) {
                tvAITip.setText("AI unavailable. Stay active & eat healthy!");
            }
        });
    }

    private void openActivityWithBMI(Class<?> cls) {
        if (bmiCategory.isEmpty()) {
            Toast.makeText(this, "Please calculate BMI first", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, cls);
        intent.putExtra("BMI_CATEGORY", bmiCategory);
        startActivity(intent);
    }
}
