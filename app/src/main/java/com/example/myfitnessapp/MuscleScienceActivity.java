package com.example.myfitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MuscleScienceActivity extends AppCompatActivity {

    LinearLayout cardChest, cardArms, cardShoulders, cardBack, cardLegs, cardAbs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muscle_science);

        cardChest = findViewById(R.id.cardChest);
        cardArms = findViewById(R.id.cardArms);
        cardShoulders = findViewById(R.id.cardShoulders);
        cardBack = findViewById(R.id.cardBack);
        cardLegs = findViewById(R.id.cardLegs);
        cardAbs = findViewById(R.id.cardAbs);

        cardChest.setOnClickListener(v -> openDetail("Chest"));
        cardArms.setOnClickListener(v -> openDetail("Arms"));
        cardShoulders.setOnClickListener(v -> openDetail("Shoulders"));
        cardBack.setOnClickListener(v -> openDetail("Back"));
        cardLegs.setOnClickListener(v -> openDetail("Legs"));
        cardAbs.setOnClickListener(v -> openDetail("Abs"));
    }

    private void openDetail(String muscleGroup) {
        Intent intent = new Intent(this, MuscleDetailActivity.class);
        intent.putExtra("MUSCLE_GROUP", muscleGroup);
        startActivity(intent);
    }
}
