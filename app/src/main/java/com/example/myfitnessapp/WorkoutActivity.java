package com.example.myfitnessapp;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class WorkoutActivity extends AppCompatActivity {

    TextView tvWorkout, tvAIWorkoutTip;
    AIHelper aiHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        tvWorkout = findViewById(R.id.tvWorkout);
        tvAIWorkoutTip = findViewById(R.id.tvAIWorkoutTip);

        aiHelper = AIHelper.getInstance();

        String bmiCategory = getIntent().getStringExtra("BMI_CATEGORY");
        if (bmiCategory == null || bmiCategory.isEmpty()) {
            tvWorkout.setText("No BMI data. Please calculate BMI first.");
            tvAIWorkoutTip.setText("AI unavailable.");
            return;
        }

        // --- Workout suggestions based on BMI ---
        switch (bmiCategory.toUpperCase()) {
            case "UNDERWEIGHT":
                tvWorkout.setText(getMuscleGainWorkout());
                break;
            case "NORMAL":
                tvWorkout.setText(getBalancedWorkout());
                break;
            case "OVERWEIGHT":
            case "OBESE":
                tvWorkout.setText(getFatLossWorkout());
                break;
            default:
                tvWorkout.setText("Standard workout:\n• Jogging\n• Pushups\n• Stretching");
                break;
        }

        // --- Fetch AI workout tip ---
        tvAIWorkoutTip.setText("Fetching AI workout tip...");
        String prompt = "Give a short workout tip for a person with BMI category " + bmiCategory;
        aiHelper.getAIResponse(prompt, new AIHelper.AIResponseCallback() {
            @Override
            public void onResponse(String response) {
                tvAIWorkoutTip.setText(response);
            }

            @Override
            public void onError(String error) {
                tvAIWorkoutTip.setText("AI unavailable. Exercise safely!");
            }
        });
    }

    private String getMuscleGainWorkout() {
        return "MUSCLE GAIN WORKOUT\n• Pushups\n• Squats\n• Dumbbell Press";
    }

    private String getBalancedWorkout() {
        return "BALANCED WORKOUT\n• Jogging\n• Plank\n• Squats";
    }

    private String getFatLossWorkout() {
        return "FAT LOSS WORKOUT\n• Cardio\n• Jump Squats\n• Mountain Climbers";
    }
}
