package com.lab.calorie;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.Date;
import java.util.List;

@Dao
public interface FoodMenuJoinDao {

    @Insert
    void insert(FoodMenuJoin foodMenuJoin);

    @Query("SELECT * FROM food_table " +
            "INNER JOIN food_menu_join " +
            "ON food_table.id = food_menu_join.foodId " +
            "WHERE food_menu_join.menuDate=:menuDate")
    List<Food> getFoodForMenu(Date menuDate);

    @Query("SELECT * FROM menu_table " +
            "INNER JOIN food_menu_join " +
            "ON menu_table.date = food_menu_join.menuDate " +
            "WHERE food_menu_join.foodId=:foodId")
    List<Menu> getMenuForFood(int foodId);
}
