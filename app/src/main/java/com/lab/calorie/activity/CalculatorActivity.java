package com.lab.calorie.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.lab.calorie.R;

public class CalculatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        initializeButtons();
    }

    private void initializeButtons() {
        Button calculateButton = findViewById(R.id.calculateButton);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent(CalculatorActivity.this, BmrResultActivity.class);
                resultIntent.putExtra("bmr_input", getInputData());
                startActivity(resultIntent);
            }
        });
    }

    private String[] getInputData() {
        final RadioGroup radioGroup = findViewById(R.id.radioGroup);
        final RadioButton mGenderInput = findViewById(radioGroup.getCheckedRadioButtonId());
        final EditText mAgeInput = findViewById(R.id.age_input);
        final EditText mHeightInput = findViewById(R.id.height_input);
        final EditText mWeightInput = findViewById(R.id.weight_input);

        String[] inputs = new String[4];
        String gender = mGenderInput.getText().toString();
        String age = mAgeInput.getText().toString();
        String height = mHeightInput.getText().toString();
        String weight = mWeightInput.getText().toString();
        inputs[0] = gender;
        inputs[1] = age;
        inputs[2] = height;
        inputs[3] = weight;

        return inputs;
    }
}
