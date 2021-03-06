package com.lab.calorie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lab.calorie.R;
import com.lab.calorie.adapter.CustomAdapter;
import com.lab.calorie.model.Food;
import com.lab.calorie.viewModel.FoodViewModel;

import java.util.List;

public class PickMenuActivity extends AppCompatActivity {
    private CustomAdapter adapter;
    private RecyclerView recyclerView;
    private TextView totalCalorieTextView;
    private TextView moreCalorieTextView;

    private int bmrValue;
    private int totalCalorie;

    private FoodViewModel mFoodViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pick_menu_activity);

        Intent intent = getIntent();
        bmrValue = (int)intent.getDoubleExtra("bmr_value", 0.0);

        totalCalorieTextView = findViewById(R.id.total_calorie);
        moreCalorieTextView = findViewById(R.id.more_calorie);

        mFoodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);
        adapter = new CustomAdapter(this, totalCalorieTextView, moreCalorieTextView, bmrValue);

        recyclerView = findViewById(R.id.customRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(PickMenuActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        mFoodViewModel.getAllFood().observe(this, new Observer<List<Food>>() {
            @Override
            public void onChanged(List<Food> foodList) {
                adapter.setAllFood(foodList);
            }
        });

        Button pickDateButton = findViewById(R.id.pick_date_button);
        pickDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Food> selectedFood = adapter.getSelectedFood();

                Bundle bundle = new Bundle();
                for (int i = 0; i < selectedFood.size(); i++) {
                    bundle.putSerializable("food_no_"+i, selectedFood.get(i));
                }

                totalCalorie = adapter.getTotalCalorie();

                Intent pickDateIntent = new Intent(PickMenuActivity.this, PickDateActivity.class);
                pickDateIntent.putExtra("food_list", bundle);
                pickDateIntent.putExtra("bmr_value", bmrValue);
                pickDateIntent.putExtra("total_calorie", totalCalorie);
                startActivity(pickDateIntent);
            }
        });
    }

}
