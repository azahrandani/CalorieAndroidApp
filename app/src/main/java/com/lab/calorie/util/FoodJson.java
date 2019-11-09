package com.lab.calorie.util;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class FoodJson implements Serializable {

    @SerializedName("name")
    private String name;

    @SerializedName("nutrients")
    private List<FoodNutrientJson> foodNutrientJsonList;

    public FoodJson(String name, List<FoodNutrientJson> foodNutrientJsonList) {
        this.name = name;
        this.foodNutrientJsonList = foodNutrientJsonList;
    }

    public String getName() {
        return name;
    }

    public List<FoodNutrientJson> getfoodNutrientList() {
        return foodNutrientJsonList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setfoodNutrientList(List<FoodNutrientJson> foodNutrientJsonList) {
        this.foodNutrientJsonList = foodNutrientJsonList;
    }

}
