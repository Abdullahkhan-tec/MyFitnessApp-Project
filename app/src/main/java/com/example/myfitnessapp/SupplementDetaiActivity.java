package com.example.myfitnessapp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SupplementDetaiActivity extends AppCompatActivity {

    ImageView imgSupplement;
    TextView tvInfo, tvAITip;
    AIHelper aiHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplement_detai);

        imgSupplement = findViewById(R.id.imgSupplement);
        tvInfo = findViewById(R.id.tvInfo);
        tvAITip = findViewById(R.id.tvAITip); // Make sure you add this TextView in XML

        aiHelper = AIHelper.getInstance();

        // Get supplement type from intent
        String type = getIntent().getStringExtra("SUPPLEMENT_TYPE");

        if (type == null) {
            tvInfo.setText("No supplement selected.");
            imgSupplement.setImageResource(R.drawable.supplement_bg);
            tvAITip.setText("AI tip unavailable.");
            return;
        }

        // Display supplement info
        switch (type.toUpperCase()) {
            case "WHEY":
                imgSupplement.setImageResource(R.drawable.whey_protien);
                tvInfo.setText(
                        "Whey Protein\n\n" +
                                "✔ Builds lean muscle\n" +
                                "✔ Fast absorption\n" +
                                "✔ Take after workout"
                );
                break;

            case "MASS":
                imgSupplement.setImageResource(R.drawable.mass_gainer);
                tvInfo.setText(
                        "Mass Gainer\n\n" +
                                "✔ High calories\n" +
                                "✔ Weight gain support\n" +
                                "✔ Use twice daily"
                );
                break;

            case "CREATINE":
                imgSupplement.setImageResource(R.drawable.creatine);
                tvInfo.setText(
                        "Creatine\n\n" +
                                "✔ Strength & power\n" +
                                "✔ Improves performance\n" +
                                "✔ Take before workout"
                );
                break;

            case "FAT":
                imgSupplement.setImageResource(R.drawable.fat_burner);
                tvInfo.setText(
                        "Fat Burner\n\n" +
                                "✔ Boosts metabolism\n" +
                                "✔ Enhances fat loss\n" +
                                "✔ Take before cardio"
                );
                break;

            default:
                imgSupplement.setImageResource(R.drawable.supplement_bg);
                tvInfo.setText("No information available.");
        }

        // Optional: AI Tip for supplement
        String prompt = "Give one short safety tip for taking " + type + " supplement";
        aiHelper.getAIResponse(prompt, new AIHelper.AIResponseCallback() {
            @Override
            public void onResponse(String response) {
                tvAITip.setText(response);
            }
            @Override
            public void onError(String error) {
                tvAITip.setText("AI unavailable. Follow supplement instructions carefully!");
            }
        });
    }
}
