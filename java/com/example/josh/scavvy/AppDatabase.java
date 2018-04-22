package com.example.josh.scavvy;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {ScavHunt.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract ScavHuntDao scavHuntDao();

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
// Create database here
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "ScavHuntDatabase")
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}