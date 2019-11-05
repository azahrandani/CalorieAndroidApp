package com.lab.calorie;

import com.google.gson.annotations.SerializedName;

public class FoodJson {

    @SerializedName("report")
    private FoodReport foodReport;

    public FoodJson(FoodReport foodReport) {
        this.foodReport = foodReport;
    }

    public FoodReport getFoodReport() {
        return foodReport;
    }

    public void setFoodReport(FoodReport foodReport) {
        this.foodReport = foodReport;
    }
}
