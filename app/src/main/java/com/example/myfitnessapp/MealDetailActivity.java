package com.example.myfitnessapp;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MealDetailActivity extends AppCompatActivity {

    TextView tvBreakfast, tvLunch, tvDinner, tvSnacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_detail); // must match XML filename

        // Bind views
        tvBreakfast = findViewById(R.id.tvBreakfast);
        tvLunch = findViewById(R.id.tvLunch);
        tvDinner = findViewById(R.id.tvDinner);
        tvSnacks = findViewById(R.id.tvSnacks);

        // Get meal type from Intent
        String mealType = getIntent().getStringExtra("MEAL_TYPE");
        if (mealType == null) mealType = "NORMAL";

        // Set meal details based on type
        switch (mealType.toUpperCase()) {
            case "GAIN":
                tvBreakfast.setText("Oatmeal + Eggs\nProtein shake");
                tvLunch.setText("Rice + Chicken + Veggies");
                tvDinner.setText("Salmon + Quinoa + Veggies");
                tvSnacks.setText("Nuts, Yogurt, Fruit");
                break;

            case "MAINTAIN":
                tvBreakfast.setText("Toast + Eggs\nFruit smoothie");
                tvLunch.setText("Brown rice + Chicken + Veggies");
                tvDinner.setText("Grilled fish + Veggies");
                tvSnacks.setText("Fruits, Yogurt");
                break;

            case "LOSS":
                tvBreakfast.setText("Oats + Protein shake");
                tvLunch.setText("Salad + Grilled chicken");
                tvDinner.setText("Vegetable soup + Lean protein");
                tvSnacks.setText("Fruits, Nuts (small portion)");
                break;

            default:
                tvBreakfast.setText("Breakfast info missing");
                tvLunch.setText("Lunch info missing");
                tvDinner.setText("Dinner info missing");
                tvSnacks.setText("Snacks info missing");
        }
    }
}
