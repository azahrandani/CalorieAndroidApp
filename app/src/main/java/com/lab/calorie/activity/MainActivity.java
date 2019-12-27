package com.lab.calorie.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import com.lab.calorie.R;
import com.lab.calorie.model.Food;
import com.lab.calorie.service.GetDataService;
import com.lab.calorie.service.RetrofitClientInstance;
import com.lab.calorie.util.CompleteFoodJson;
import com.lab.calorie.util.FoodJson;
import com.lab.calorie.viewModel.FoodViewModel;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private FoodViewModel mFoodViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFoodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);
        initializeButtons();
        checkInternetConnection();
    }

    private void initializeButtons() {
        Button toCalculatorButton = findViewById(R.id.button_to_calculator);
        toCalculatorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CalculatorActivity.class);
                startActivity(intent);
            }
        });

        Button toTimerButton = findViewById(R.id.button_to_timer);
        toTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TimerActivity.class);
                startActivity(intent);
            }
        });

        Button toMenuButton = findViewById(R.id.button_to_menu);
        toMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListMenuActivity.class);
                startActivity(intent);
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

    public void callFoodApi() {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<CompleteFoodJson> call = service.getFoodJson();
        call.enqueue(new Callback<CompleteFoodJson>() {
            @Override
            public void onResponse(Call<CompleteFoodJson> call, Response<CompleteFoodJson> response) {
                List<FoodJson> foodJsonList = response.body().getFoodReport().getFoodJsonList();
                insertAllFood(foodJsonList);
            }

            @Override
            public void onFailure(Call<CompleteFoodJson> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void insertAllFood(List<FoodJson> foodJsonList) {
        for (FoodJson food : foodJsonList) {
            Food newFood = new Food(food.getName(), food.getfoodNutrientList().get(0).getValue());
            mFoodViewModel.insert(newFood);
        }
    }

    private void checkInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (isConnected) {
            callFoodApi();
        } else {
            showAlertDialog();
        }
    }

    private void showAlertDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(getResources().getString(R.string.no_internet));
        alertDialog.setMessage(getResources().getString(R.string.this_app_needs));
        alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialog.setButton(Dialog.BUTTON_POSITIVE, getResources().getString(R.string.exit_app), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        alertDialog.show();
    }

}
