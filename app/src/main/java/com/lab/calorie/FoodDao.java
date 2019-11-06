package com.lab.calorie;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Food food);

    @Query("SELECT * from food_table")
    LiveData<List<Food>> getAllFood();

    @Query("DELETE from food_table")
    void deleteAll();
}
