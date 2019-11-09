package com.lab.calorie.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.lab.calorie.model.Bmr;

@Dao
public interface BmrDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Bmr bmr);

    @Query("SELECT * from bmr_table")
    LiveData<Bmr> getBMr();

    @Query("DELETE from bmr_table")
    void deleteAll();

}
