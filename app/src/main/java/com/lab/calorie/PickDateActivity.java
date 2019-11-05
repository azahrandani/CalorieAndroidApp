package com.lab.calorie;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.ArrayList;
import java.util.List;

public class PickDateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle foodListBundle = getIntent().getBundleExtra("food_list");
        final int bmrValue = getIntent().getIntExtra("bmr_value", 0);

        SelectedFoodFragment selectedFoodFragment = new SelectedFoodFragment();
        selectedFoodFragment.setArguments(foodListBundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.selectedFoodFragment, selectedFoodFragment);
        fragmentTransaction.commit();

        setContentView(R.layout.activity_pick_date);

        final DatePicker datePicker = findViewById(R.id.datePicker1);

        Button saveDateMenuButton = findViewById(R.id.save_menu_date_button);
        saveDateMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("tanggal " + datePicker.getDayOfMonth() + " " + datePicker.getMonth() + " " + datePicker.getYear());
                System.out.println("bmr value " + bmrValue);
            }
        });
    }

}
