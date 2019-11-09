package com.lab.calorie;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class FoodMenuJoinRepository {

    private FoodMenuJoinDao mFoodMenuJoinDao;
    private LiveData<List<FoodMenuJoin>> mAllFoodMenuJoin;
    private LiveData<List<Food>> mFoodOfMenu;

    public FoodMenuJoinRepository(Application application) {
        CalorieRoomDatabase db = CalorieRoomDatabase.getDatabase(application);
        mFoodMenuJoinDao = db.foodMenuJoinDao();
        mAllFoodMenuJoin = mFoodMenuJoinDao.getAllFoodMenuJoin();
    }

    public LiveData<List<FoodMenuJoin>> getAllFoodMenuJoin() {
        return mAllFoodMenuJoin;
    }

    public LiveData<List<Food>> getFoodForMenu(int menuId) {
        return mFoodMenuJoinDao.getFoodForMenu(menuId);
    }

    public void insert(FoodMenuJoin foodmenujoin) {
        new insertAsyncTask(mFoodMenuJoinDao).execute(foodmenujoin);
    }

    private static class insertAsyncTask extends AsyncTask<FoodMenuJoin, Void, Void> {

        private FoodMenuJoinDao mAsyncTaskDao;

        insertAsyncTask(FoodMenuJoinDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final FoodMenuJoin... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

}
