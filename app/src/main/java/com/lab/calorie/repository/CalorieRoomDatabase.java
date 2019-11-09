package com.lab.calorie.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.lab.calorie.model.Bmr;
import com.lab.calorie.dao.BmrDao;
import com.lab.calorie.model.Food;
import com.lab.calorie.dao.FoodDao;
import com.lab.calorie.model.FoodMenuJoin;
import com.lab.calorie.dao.FoodMenuJoinDao;
import com.lab.calorie.model.Menu;
import com.lab.calorie.dao.MenuDao;

@Database(entities = {Bmr.class, Food.class, Menu.class, FoodMenuJoin.class}, version = 1, exportSchema = false)
public abstract class CalorieRoomDatabase extends RoomDatabase {

    public abstract BmrDao bmrDao();
    public abstract FoodDao foodDao();
    public abstract MenuDao menuDao();
    public abstract FoodMenuJoinDao foodMenuJoinDao();

    private static volatile CalorieRoomDatabase INSTANCE;

    public static CalorieRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CalorieRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                CalorieRoomDatabase.class, "calorie_database")
                                .addCallback(sRoomDatabaseCallback)
                                .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final BmrDao mBmrDao;
        private final FoodDao mFoodDao;
        private final MenuDao mMenuDao;
        private final FoodMenuJoinDao mFoodMenuJoinDao;

        PopulateDbAsync(CalorieRoomDatabase db) {
            mBmrDao = db.bmrDao();
            mFoodDao = db.foodDao();
            mMenuDao = db.menuDao();
            mFoodMenuJoinDao = db.foodMenuJoinDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            return null;
        }
    }
}
