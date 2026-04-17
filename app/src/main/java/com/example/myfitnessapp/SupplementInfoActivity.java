package com.example.myfitnessapp;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SupplementInfoActivity extends AppCompatActivity {

    LinearLayout layoutSelection, layoutInfo;
    TextView tvTitle, tvInfo, tvAITip;
    AIHelper aiHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplement_info);

        layoutSelection = findViewById(R.id.layoutSelection);
        layoutInfo = findViewById(R.id.layoutInfo);
        tvTitle = findViewById(R.id.tvSupplementTitle);
        tvInfo = findViewById(R.id.tvSupplementInfo);
        tvAITip = findViewById(R.id.tvAITip); // add TextView in XML for AI tips

        aiHelper = AIHelper.getInstance();

        // Setup supplement cards
        setupCard(R.id.cardCreatine, "Creatine",
                "⚠ Kidney disease: Use low dose\n" +
                        "⚠ Dehydration risk\n" +
                        "✔ Drink plenty of water\n" +
                        "✔ Avoid if kidney issues without doctor advice");

        setupCard(R.id.cardWhey, "Whey Protein",
                "⚠ Lactose intolerance\n" +
                        "⚠ Kidney patients should limit intake\n" +
                        "✔ Balance with natural food");

        setupCard(R.id.cardFatBurner, "Fat Burner",
                "⚠ Heart disease risk\n" +
                        "⚠ High BP patients avoid\n" +
                        "⚠ Anxiety & sleep issues");

        setupCard(R.id.cardMassGainer, "Mass Gainer",
                "⚠ Diabetes patients avoid\n" +
                        "⚠ Excess calories → fat gain\n" +
                        "✔ Use controlled portions");

        // Back button
        findViewById(R.id.btnBack).setOnClickListener(v -> {
            layoutInfo.setVisibility(View.GONE);
            layoutSelection.setVisibility(View.VISIBLE);
        });
    }

    // Helper to setup card clicks
    private void setupCard(int cardId, String title, String info) {
        View card = findViewById(cardId);
        if (card != null) {
            card.setOnClickListener(v -> showInfo(title, info));
        } else {
            Toast.makeText(this, "Card missing: " + title, Toast.LENGTH_SHORT).show();
        }
    }

    // Show info and load AI safety tips
    private void showInfo(String title, String info) {
        layoutSelection.setVisibility(View.GONE);
        layoutInfo.setVisibility(View.VISIBLE);
        tvTitle.setText(title);
        tvInfo.setText(info);

        tvAITip.setText("Fetching AI safety tip...");
        String prompt = "Give a short safety tip for using " + title + " supplement safely";
        aiHelper.getAIResponse(prompt, new AIHelper.AIResponseCallback() {
            @Override
            public void onResponse(String response) {
                tvAITip.setText(response);
            }

            @Override
            public void onError(String error) {
                tvAITip.setText("AI unavailable. Follow standard precautions!");
            }
        });
    }
}
