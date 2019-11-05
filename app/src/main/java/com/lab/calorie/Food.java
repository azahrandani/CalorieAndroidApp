package com.lab.calorie;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Food implements Serializable {

    @SerializedName("name")
    private String name;

    @SerializedName("nutrients")
    private List<FoodNutrient> foodNutrientList;

    public Food(String name, List<FoodNutrient> foodNutrientList) {
        this.name = name;
        this.foodNutrientList = foodNutrientList;
    }

    public String getName() {
        return name;
    }

    public List<FoodNutrient> getfoodNutrientList() {
        return foodNutrientList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setfoodNutrientList(List<FoodNutrient> foodNutrientList) {
        this.foodNutrientList = foodNutrientList;
    }

}
