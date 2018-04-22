package com.example.josh.scavvy;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class ScavItemRepository {
    private ScavHuntDao scavHuntDao;
    private LiveData<List<ScavItem>> scavItems;

    ScavItemRepository(Application application, int scavHuntID) {
        AppDatabase db = AppDatabase.getDatabase(application);
        scavHuntDao = db.scavHuntDao();
        scavItems = scavHuntDao.getScavItems(scavHuntID);
    }

    public void insert (ScavItem scavItem) {
        new ScavItemRepository.insertAsyncTask(scavHuntDao).execute(scavItem);
    }

    LiveData<List<ScavItem>> getAllItems() {
        return scavItems;
    }

    private static class insertAsyncTask extends AsyncTask<ScavItem, Void, Void> {

        private ScavHuntDao mAsyncTaskDao;

        insertAsyncTask(ScavHuntDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final ScavItem... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
