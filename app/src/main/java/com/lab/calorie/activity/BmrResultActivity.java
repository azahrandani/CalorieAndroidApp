package com.lab.calorie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.lab.calorie.R;
import com.lab.calorie.model.Bmr;
import com.lab.calorie.viewModel.BmrViewModel;

public class BmrResultActivity extends AppCompatActivity {
    private BmrViewModel mBmrViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bmr_result);

        mBmrViewModel = new ViewModelProvider(this).get(BmrViewModel.class);

        String[] inputs = getIntent().getStringArrayExtra("bmr_input");
        Boolean isMale = inputs[0].equals("Male") ? true : false;
        int age = Integer.parseInt(inputs[1]);
        final int height = Integer.parseInt(inputs[2]);
        final int weight = Integer.parseInt(inputs[3]);

        final Bmr bmr = new Bmr(isMale, age, height, weight);
        mBmrViewModel.insert(bmr);

        Button pickMenuButton = findViewById(R.id.pick_menu_button);
        pickMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickMenuIntent = new Intent(BmrResultActivity.this, PickMenuActivity.class);
                pickMenuIntent.putExtra("bmr_value", bmr.getValue());
                startActivity(pickMenuIntent);
            }
        });

        TextView bmiTextClick = findViewById(R.id.bmi_text_click);
        bmiTextClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent animationIntent = new Intent(BmrResultActivity.this, AnimationActivity.class);
                animationIntent.putExtra("height", height);
                animationIntent.putExtra("weight", weight);
                startActivity(animationIntent);
            }
        });
    }

}
