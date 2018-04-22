package com.example.josh.scavvy;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {ScavHunt.class, ScavItem.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final ScavHuntDao mDao;

        PopulateDbAsync(AppDatabase db) {
            mDao = db.scavHuntDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAllScavHunts();
            ScavHunt bigAnimals = new ScavHunt("Mammals", 0);
            mDao.insert(bigAnimals);
            ScavHunt neckbeard_shit = new ScavHunt("Neckbeard Shit", 0);
            mDao.insert(neckbeard_shit);
            return null;
        }
    }

    private static AppDatabase INSTANCE;

    public abstract ScavHuntDao scavHuntDao();

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
// Create database here
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "ScavHuntDatabase")
                    .addCallback(sRoomDatabaseCallback)
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){
                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };
}