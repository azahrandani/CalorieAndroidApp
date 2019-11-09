package com.lab.calorie;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FoodMenuJoinDao {

    @Insert
    void insert(FoodMenuJoin foodMenuJoin);

    @Query("SELECT food_table.id, name, kcal FROM food_table " +
            "INNER JOIN food_menu_join_table " +
            "ON food_table.id = food_menu_join_table.foodId " +
            "WHERE food_menu_join_table.menuId=:menuId")
    LiveData<List<Food>> getFoodForMenu(int menuId);

    @Query("SELECT menu_table.id, calendar, calorieValue, bmrValue FROM menu_table " +
            "INNER JOIN food_menu_join_table " +
            "ON menu_table.calendar = food_menu_join_table.menuId " +
            "WHERE food_menu_join_table.foodId=:foodId")
    List<Menu> getMenuForFood(int foodId);

    @Query("SELECT * from food_menu_join_table")
    LiveData<List<FoodMenuJoin>> getAllFoodMenuJoin();

    @Query("DELETE from food_menu_join_table")
    void deleteAll();
}
