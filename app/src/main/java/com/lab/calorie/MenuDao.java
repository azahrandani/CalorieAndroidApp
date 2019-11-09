package com.lab.calorie;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MenuDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Menu menu);

    @Query("SELECT * from menu_table")
    LiveData<List<Menu>> getAllMenu();

    @Query("SELECT * from menu_table " +
            "WHERE id=:id")
    LiveData<Menu> getMenuById(int id);

    @Query("DELETE from menu_table")
    void deleteAll();
}
