package com.example.myfitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SupplementActivity extends AppCompatActivity {

    LinearLayout cardWhey, cardMass, cardCreatine, cardFat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplement);

        cardWhey = findViewById(R.id.cardWhey);
        cardMass = findViewById(R.id.cardMass);
        cardCreatine = findViewById(R.id.cardCreatine);
        cardFat = findViewById(R.id.cardFat);

        // Get BMI category from intent
        String bmi = getIntent().getStringExtra("BMI_CATEGORY");

        // If BMI is missing, redirect to BMI Activity
        if (bmi == null || bmi.isEmpty()) {
            Toast.makeText(this, "Please calculate your BMI first!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, BmiActivity.class));
            finish();
            return;
        }

        // Hide all cards initially
        cardWhey.setVisibility(View.GONE);
        cardMass.setVisibility(View.GONE);
        cardCreatine.setVisibility(View.GONE);
        cardFat.setVisibility(View.GONE);

        // Show cards based on BMI category
        switch (bmi.toUpperCase()) {
            case "UNDERWEIGHT":
                cardWhey.setVisibility(View.VISIBLE);
                cardMass.setVisibility(View.VISIBLE);
                cardCreatine.setVisibility(View.VISIBLE);
                break;
            case "NORMAL":
                cardWhey.setVisibility(View.VISIBLE);
                cardCreatine.setVisibility(View.VISIBLE);
                break;
            case "OVERWEIGHT":
            case "OBESE":
            default:
                cardFat.setVisibility(View.VISIBLE);
                break;
        }

        // Set click listeners
        cardWhey.setOnClickListener(v -> openDetail("WHEY", bmi));
        cardMass.setOnClickListener(v -> openDetail("MASS", bmi));
        cardCreatine.setOnClickListener(v -> openDetail("CREATINE", bmi));
        cardFat.setOnClickListener(v -> openDetail("FAT", bmi));
    }

    private void openDetail(String type, String bmiCategory) {
        Intent intent = new Intent(this, SupplementDetaiActivity.class); // fixed typo
        intent.putExtra("SUPPLEMENT_TYPE", type);
        intent.putExtra("BMI_CATEGORY", bmiCategory); // pass BMI if needed
        startActivity(intent);
    }
}
