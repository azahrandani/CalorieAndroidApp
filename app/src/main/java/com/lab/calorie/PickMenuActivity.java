package com.lab.calorie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PickMenuActivity extends AppCompatActivity {
    private CustomAdapter adapter;
    private RecyclerView recyclerView;
    private TextView totalCalorieTextView;
    private TextView moreCalorieTextView;

    private int bmrValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pick_menu_activity);

        Intent intent = getIntent();
        bmrValue = (int)intent.getDoubleExtra("bmr_value", 0.0);

        totalCalorieTextView = findViewById(R.id.total_calorie);
        moreCalorieTextView = findViewById(R.id.more_calorie);

        /*Create handle for the RetrofitInstance interface*/
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<FoodJson> call = service.getFoodJson();
        call.enqueue(new Callback<FoodJson>() {
            @Override
            public void onResponse(Call<FoodJson> call, Response<FoodJson> response) {
                List<Food> foodList = response.body().getFoodReport().getFoodList();
                generateDataList(foodList);
            }

            @Override
            public void onFailure(Call<FoodJson> call, Throwable t) {
                System.out.println("pokonya fail aja " + t.getMessage());
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
    private void generateDataList(List<Food> foodList) {
        recyclerView = findViewById(R.id.customRecyclerView);
        adapter = new CustomAdapter(this, foodList, totalCalorieTextView, moreCalorieTextView, bmrValue);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(PickMenuActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

}
