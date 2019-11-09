package com.lab.calorie;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

public class PickDateActivity extends AppCompatActivity {

    private static final int MY_CAL_WRITE_REQ = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle foodListBundle = getIntent().getBundleExtra("food_list");
        final int bmrValue = getIntent().getIntExtra("bmr_value", 0);
        final int totalCalorie = getIntent().getIntExtra("total_calorie", 0);

        SelectedFoodFragment selectedFoodFragment = new SelectedFoodFragment();
        selectedFoodFragment.setArguments(foodListBundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.selectedFoodFragment, selectedFoodFragment);
        fragmentTransaction.commit();

        setContentView(R.layout.activity_pick_date);

        if (ActivityCompat.checkSelfPermission(PickDateActivity.this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(PickDateActivity.this, new String[]{Manifest.permission.WRITE_CALENDAR}, MY_CAL_WRITE_REQ);
        }

        final DatePicker datePicker = findViewById(R.id.datePicker1);

        Button saveDateMenuButton = findViewById(R.id.save_menu_date_button);
        saveDateMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int date = datePicker.getDayOfMonth();
                int month = (datePicker.getMonth() + 1)%12;
                int year = month == 0 ? datePicker.getYear()+1 : datePicker.getYear();

                String dateString = date + "/" + month + "/" + year;
                System.out.println("tanggal " + dateString);
                System.out.println("bmr value " + bmrValue);

                Intent successSaveIntent = new Intent(PickDateActivity.this, SuccessSaveActivity.class);
                successSaveIntent.putExtra("food_list", foodListBundle);
                successSaveIntent.putExtra("date_string", dateString);
                successSaveIntent.putExtra("bmr_value", bmrValue);
                successSaveIntent.putExtra("total_calorie", totalCalorie);
                startActivity(successSaveIntent);
            }
        });
    }

}
