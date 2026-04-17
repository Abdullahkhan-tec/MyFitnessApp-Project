package com.example.myfitnessapp;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class MuscleDetailActivity extends AppCompatActivity {

    TextView tvMuscleTitle, tvMuscleNames, tvAIMuscleTip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muscle_detail);

        tvMuscleTitle = findViewById(R.id.tvMuscleTitle);
        tvMuscleNames = findViewById(R.id.tvMuscleNames);
        tvAIMuscleTip = findViewById(R.id.tvAIMuscleTip);

        String muscleGroup = getIntent().getStringExtra("MUSCLE_GROUP");
        if (muscleGroup == null) muscleGroup = "Muscle Group";

        tvMuscleTitle.setText(muscleGroup);

        Map<String, String> muscleMap = new HashMap<>();
        muscleMap.put("Chest", "Chest – Pectoralis Major\nChest Minor – Pectoralis Minor");
        muscleMap.put("Arms", "Biceps – Biceps Brachii\nTriceps – Triceps Brachii\nForearm – Flexor & Extensor");
        muscleMap.put("Shoulders", "Deltoid – Anterior, Lateral, Posterior\nRotator Cuff Muscles");
        muscleMap.put("Back", "Latissimus Dorsi\nTrapezius\nRhomboids\nErector Spinae");
        muscleMap.put("Legs", "Quadriceps Femoris\nHamstrings\nAdductors\nGluteus Maximus");
        muscleMap.put("Abs", "Rectus Abdominis\nObliques\nTransverse Abdominis");

        tvMuscleNames.setText(muscleMap.getOrDefault(muscleGroup, "No info available"));

        // Optional: add AI tip dynamically here
        tvAIMuscleTip.setText("Train " + muscleGroup + " regularly for better strength and definition!");
    }
}
