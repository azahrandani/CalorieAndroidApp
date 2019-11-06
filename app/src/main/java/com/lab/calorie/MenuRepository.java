package com.lab.calorie;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class MenuRepository {

    private MenuDao mMenuDao;
    private LiveData<List<Menu>> mAllMenu;

    public MenuRepository(Application application) {
        MenuRoomDatabase db = MenuRoomDatabase.getDatabase(application);
        mMenuDao = db.menuDao();
        mAllMenu = mMenuDao.getAllMenu();
    }

    public LiveData<List<Menu>> getAllMenu() {
        return mAllMenu;
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
