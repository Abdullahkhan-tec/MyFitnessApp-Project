package com.example.myfitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class BmiActivity extends AppCompatActivity {

    EditText etHeight, etWeight;
    Button btnCalculate;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);

        etHeight = findViewById(R.id.etHeight);
        etWeight = findViewById(R.id.etWeight);
        btnCalculate = findViewById(R.id.btnCalculate);
        tvResult = findViewById(R.id.tvResult);

        btnCalculate.setOnClickListener(v -> calculateBMI());
    }

    private void calculateBMI() {
        String heightStr = etHeight.getText().toString();
        String weightStr = etWeight.getText().toString();

        if (heightStr.isEmpty() || weightStr.isEmpty()) {
            Toast.makeText(this, "Enter height & weight", Toast.LENGTH_SHORT).show();
            return;
        }

        double heightM = Double.parseDouble(heightStr) / 100;
        double weightKg = Double.parseDouble(weightStr);
        double bmi = weightKg / (heightM * heightM);

        String bmiCategory;
        if (bmi < 18.5) bmiCategory = "UNDERWEIGHT";
        else if (bmi < 25) bmiCategory = "NORMAL";
        else if (bmi < 30) bmiCategory = "OVERWEIGHT";
        else bmiCategory = "OBESE";

        tvResult.setText("BMI: " + String.format("%.2f", bmi) + "\nCategory: " + bmiCategory);

        // Pass BMI category to Dashboard
        Intent intent = new Intent(BmiActivity.this, DashboardActivity.class);
        intent.putExtra("BMI_CATEGORY", bmiCategory);
        startActivity(intent);
    }
}
