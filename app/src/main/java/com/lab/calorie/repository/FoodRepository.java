package com.lab.calorie.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.lab.calorie.model.Food;
import com.lab.calorie.dao.FoodDao;

import java.util.List;

public class FoodRepository {

    private FoodDao mFoodDao;
    private LiveData<List<Food>> mAllFood;

    public FoodRepository(Application application) {
        CalorieRoomDatabase db = CalorieRoomDatabase.getDatabase(application);
        mFoodDao = db.foodDao();
        mAllFood = mFoodDao.getAllFood();
    }

    public LiveData<List<Food>> getAllFood() {
        return mAllFood;
    }

    public void insert(Food food) {
        new insertAsyncTask(mFoodDao).execute(food);
    }

    private static class insertAsyncTask extends AsyncTask<Food, Void, Void> {

        private FoodDao mAsyncTaskDao;

        insertAsyncTask(FoodDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Food... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

}
