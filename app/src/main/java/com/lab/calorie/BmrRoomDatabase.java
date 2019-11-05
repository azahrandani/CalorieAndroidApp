package com.lab.calorie;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Bmr.class}, version = 1, exportSchema = false)
public abstract class BmrRoomDatabase extends RoomDatabase {

    public abstract BmrDao bmrDao();

    private static volatile BmrRoomDatabase INSTANCE;

    static BmrRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (BmrRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                BmrRoomDatabase.class, "bmr_database")
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

        private final BmrDao mDao;

        PopulateDbAsync(BmrRoomDatabase db) {
            mDao = db.bmrDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            System.out.println("masa sihhchchch");
            return null;
        }
    }
}
