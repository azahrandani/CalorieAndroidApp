package com.lab.calorie;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FoodNutrient implements Serializable {

    @SerializedName("value")
    private int value;

    public FoodNutrient(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
