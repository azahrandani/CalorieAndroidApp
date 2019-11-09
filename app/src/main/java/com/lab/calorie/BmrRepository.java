package com.lab.calorie;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

public class BmrRepository {

    private BmrDao mBmrDao;
    private LiveData<Bmr> mTheBmr;

    public BmrRepository(Application application) {
        CalorieRoomDatabase db = CalorieRoomDatabase.getDatabase(application);
        mBmrDao = db.bmrDao();
        mTheBmr = mBmrDao.getBMr();
    }

    public LiveData<Bmr> getBmr() {
        return mTheBmr;
    }

    public void insert(Bmr bmr) {
        new insertAsyncTask(mBmrDao).execute(bmr);
    }

    private static class insertAsyncTask extends AsyncTask<Bmr, Void, Void> {

        private BmrDao mAsyncTaskDao;

        insertAsyncTask(BmrDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Bmr... params) {
            mAsyncTaskDao.deleteAll();
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

}
