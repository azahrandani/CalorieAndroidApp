package com.lab.calorie.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "food_menu_join_table",
        primaryKeys = {"foodId", "menuId"},
        foreignKeys = {
                    @ForeignKey(entity = Food.class,
                                parentColumns = "id",
                                childColumns = "foodId",
                                onDelete = CASCADE),
                    @ForeignKey(entity = Menu.class,
                                parentColumns = "id",
                                childColumns = "menuId",
                                onDelete = CASCADE)
                    })
public class FoodMenuJoin {

    @NonNull
    @ColumnInfo(name = "foodId")
    private int foodId;

    @NonNull
    @ColumnInfo(name = "menuId")
    private int menuId;

    public FoodMenuJoin(int foodId, int menuId) {
        this.foodId = foodId;
        this.menuId = menuId;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }
}
