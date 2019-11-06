package com.lab.calorie;

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

import java.util.List;

public class PickMenuActivity extends AppCompatActivity {
    private CustomAdapter adapter;
    private RecyclerView recyclerView;
    private TextView totalCalorieTextView;
    private TextView moreCalorieTextView;

    private int bmrValue;

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

        System.out.println("this is somewhere in pick menu activity");
        /*Create handle for the RetrofitInstance interface*/
//        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
//        Call<CompleteFoodJson> call = service.getFoodJson();
//        call.enqueue(new Callback<CompleteFoodJson>() {
//            @Override
//            public void onResponse(Call<CompleteFoodJson> call, Response<CompleteFoodJson> response) {
//                List<FoodJson> foodJsonList = response.body().getFoodReport().getFoodJsonList();
//                generateDataList(foodJsonList);
//            }
//
//            @Override
//            public void onFailure(Call<CompleteFoodJson> call, Throwable t) {
//                System.out.println("pokonya fail aja " + t.getMessage());
//            }
//        });

        mFoodViewModel.getAllFood().observe(this, new Observer<List<Food>>() {
            @Override
            public void onChanged(List<Food> foodList) {
                System.out.println("masuk ke onchanged di pick menu activity");
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
                    System.out.println(selectedFood.get(i).getName());
                    bundle.putSerializable("food_no_"+i, selectedFood.get(i));
                }
                Intent pickDateIntent = new Intent(PickMenuActivity.this, PickDateActivity.class);
                pickDateIntent.putExtra("food_list", bundle);
                pickDateIntent.putExtra("bmr_value", bmrValue);
                startActivity(pickDateIntent);
            }
        });
    }

    /*Method to generate List of data using RecyclerView with custom adapter*/
//    private void generateDataList(List<FoodJson> foodJsonList) {
//        recyclerView = findViewById(R.id.customRecyclerView);
//        adapter = new CustomAdapter(this, totalCalorieTextView, moreCalorieTextView, bmrValue);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(PickMenuActivity.this);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(adapter);
//    }

}
