package com.example.Database.ScavHunt;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.Database.AppDatabase;

import java.util.List;

public class ScavHuntRepository {

    private ScavHuntDao scavHuntDao;
    private LiveData<List<ScavHunt>> scavHunts;

    ScavHuntRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        scavHuntDao = db.scavHuntDao();
        scavHunts = scavHuntDao.getAllScavHunts();
    }

    public void insert (ScavHunt scavHunt) {
        new insertAsyncTask(scavHuntDao).execute(scavHunt);
    }


    LiveData<List<ScavHunt>> getAllHunts() {
        return scavHunts;
    }

    private static class insertAsyncTask extends AsyncTask<ScavHunt, Void, Void> {

        private ScavHuntDao mAsyncTaskDao;

        insertAsyncTask(ScavHuntDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final ScavHunt... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
