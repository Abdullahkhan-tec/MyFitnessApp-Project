package com.example.myfitnessapp;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class ProgressTrackerActivity extends AppCompatActivity {

    Spinner spinnerWeight, spinnerWorkout;
    Button btnAddLog;
    LinearLayout logsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_tracker);

        spinnerWeight = findViewById(R.id.spinnerWeight);
        spinnerWorkout = findViewById(R.id.spinnerWorkout);
        btnAddLog = findViewById(R.id.btnAddLog);
        logsContainer = findViewById(R.id.logsContainer);

        // Weight options
        String[] weightOptions = {"Select Weight", "30 kg", "35 kg", "40 kg", "45 kg", "50 kg", "55 kg","60 kg", "65 kg", "70 kg", "75 kg", "80 kg", "85 kg","90 kg", "95 kg", "100 kg" };
        ArrayAdapter<String> weightAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, weightOptions);
        weightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWeight.setAdapter(weightAdapter);

        // Workout options
        String[] workoutOptions = {"Select Workout", "Cardio", "Strength","Muscle Building","Fat Loss", "Yoga", "HIIT", "Rest Day"};
        ArrayAdapter<String> workoutAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, workoutOptions);
        workoutAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWorkout.setAdapter(workoutAdapter);

        btnAddLog.setOnClickListener(v -> addLog());
    }

    private void addLog() {
        String weight = spinnerWeight.getSelectedItem().toString();
        String workout = spinnerWorkout.getSelectedItem().toString();

        if(weight.equals("Select Weight") || workout.equals("Select Workout")) {
            Toast.makeText(this, "Select weight and workout", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a log TextView dynamically
        TextView log = new TextView(this);
        log.setText("Weight: " + weight + "\nWorkout: " + workout);
        log.setPadding(16,16,16,16);
        log.setBackgroundResource(R.drawable.card_background);
        log.setTextColor(getResources().getColor(android.R.color.black));
        log.setTextSize(16);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0,0,0,16);
        log.setLayoutParams(params);

        logsContainer.addView(log);

        spinnerWeight.setSelection(0);  // Reset weight spinner
        spinnerWorkout.setSelection(0); // Reset workout spinner
    }
}
