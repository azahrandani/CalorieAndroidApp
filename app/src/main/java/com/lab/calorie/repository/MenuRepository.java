package com.lab.calorie.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.lab.calorie.model.Menu;
import com.lab.calorie.dao.MenuDao;

import java.util.List;

public class MenuRepository {

    private MenuDao mMenuDao;
    private LiveData<List<Menu>> mAllMenu;

    public MenuRepository(Application application) {
        CalorieRoomDatabase db = CalorieRoomDatabase.getDatabase(application);
        mMenuDao = db.menuDao();
        mAllMenu = mMenuDao.getAllMenu();
    }

    public LiveData<List<Menu>> getAllMenu() {
        return mAllMenu;
    }

    public LiveData<Menu> getMenuById(int id) {
        return mMenuDao.getMenuById(id);
    }

    public void insert(Menu menu) {
        new insertAsyncTask(mMenuDao).execute(menu);
    }

    private static class insertAsyncTask extends AsyncTask<Menu, Void, Void> {

        private MenuDao mAsyncTaskDao;

        insertAsyncTask(MenuDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Menu... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

}
