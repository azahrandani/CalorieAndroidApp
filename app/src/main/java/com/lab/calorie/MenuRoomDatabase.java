package com.lab.calorie;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Menu.class}, version = 1)
public abstract class MenuRoomDatabase extends RoomDatabase {

    public abstract MenuDao menuDao();

    private static volatile MenuRoomDatabase INSTANCE;

    static MenuRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MenuRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MenuRoomDatabase.class, "menu_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {
                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final MenuDao mDao;

        PopulateDbAsync(MenuRoomDatabase db) {
            mDao = db.menuDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
