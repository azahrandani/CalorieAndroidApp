package com.lab.calorie.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.lab.calorie.util.CompleteFoodJson;
import com.lab.calorie.util.FoodJson;
import com.lab.calorie.service.GetDataService;
import com.lab.calorie.R;
import com.lab.calorie.service.RetrofitClientInstance;
import com.lab.calorie.model.Food;
import com.lab.calorie.viewModel.FoodViewModel;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalculatorActivity extends AppCompatActivity {

    private FoodViewModel mFoodViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        mFoodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);

        initializeButtons();
        checkInternetConnection();
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
        alertDialog.setTitle("No internet connection");
        alertDialog.setMessage("This app needs internet connection. Please make sure you're online first.");
        alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
        alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Exit app", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        alertDialog.show();
    }
}
