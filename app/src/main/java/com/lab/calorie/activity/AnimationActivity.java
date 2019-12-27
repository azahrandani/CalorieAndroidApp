package com.lab.calorie.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.lab.calorie.R;
import com.lab.calorie.animation.Stage;

public class AnimationActivity extends AppCompatActivity {

    private Stage stage;
    public native String calculateBMI(int height, int weight);

    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int height = getIntent().getIntExtra("height", 0);
        int weight = getIntent().getIntExtra("weight", 0);

        String bmiFromJni = calculateBMI(height, weight);

        View view = LayoutInflater.from(this).inflate(R.layout.activity_animation, null);
        stage = view.findViewById(R.id.animation_stage);
        stage.setBmiStatus(getBMICategory(bmiFromJni));

        TextView bmiValue = view.findViewById(R.id.bmi_value);
        bmiValue.setText(bmiFromJni);

        setContentView(view);

        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    protected String getBMICategory(String bmi) {
        Float bmiFloat = Float.parseFloat(bmi);
        if (bmiFloat < 18.50) {
            return "underweight";
        } else if (bmiFloat >= 18.50 && bmiFloat <= 24.99) {
            return "normal";
        } else {
            return "overweight";
        }
    }

}
