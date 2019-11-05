package com.lab.calorie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent foodApiIntent = new Intent(this, FoodApiService.class);
        startService(foodApiIntent);

        Button calculateButton = findViewById(R.id.calculateButton);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent(MainActivity.this, BmrResultActivity.class);
                resultIntent.putExtra("bmr_input", getInputData());
                startActivity(resultIntent);
            }
        });

        Button changeLanguageButton = findViewById(R.id.change_language_button);
        changeLanguageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentLanguage = getResources().getConfiguration().locale.getLanguage();
                if (currentLanguage == "en") {
                    setLocale("in");
                } else {
                    setLocale("en");
                }
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

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, MainActivity.class);
        finish();
        startActivity(refresh);
    }
}
