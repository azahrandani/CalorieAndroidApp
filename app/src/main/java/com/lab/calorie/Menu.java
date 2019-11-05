package com.lab.calorie;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.List;

@Entity(tableName = "menu_table")
public class Menu {

    private List<Food> foodList;

    @PrimaryKey
    @ColumnInfo(name = "date")
    private Date date;

    @ColumnInfo(name = "calorieValue")
    private int calorieValue;

    @ColumnInfo(name = "bmrValue")
    private int bmrValue;

    public List<Food> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<Food> foodList) {
        this.foodList = foodList;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCalorieValue() {
        return calorieValue;
    }

    public void setCalorieValue(int calorieValue) {
        this.calorieValue = calorieValue;
    }

    public int getBmrValue() {
        return bmrValue;
    }

    public void setBmrValue(int bmrValue) {
        this.bmrValue = bmrValue;
    }
}
