package com.lab.calorie.util;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FoodNutrientJson implements Serializable {

    @SerializedName("value")
    private int value;

    public FoodNutrientJson(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
