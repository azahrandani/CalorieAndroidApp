package com.lab.calorie;

import android.view.Menu;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import java.util.Date;

@Entity(tableName = "food_menu_join",
        primaryKeys = {"foodId", "menuDate"},
        foreignKeys = {
                    @ForeignKey(entity = Food.class,
                                parentColumns = "id",
                                childColumns = "foodId"),
                    @ForeignKey(entity = Menu.class,
                                parentColumns = "date",
                                childColumns = "menuDate")
                    })
public class FoodMenuJoin {

    @ColumnInfo(name = "foodId")
    private int foodId;

    @ColumnInfo(name = "menuDate")
    private Date date;

    public FoodMenuJoin(int foodId, Date date) {
        this.foodId = foodId;
        this.date = date;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
