package com.example.Database.ScavHunt;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.Database.ScavItem.ScavItem;

import java.util.List;

@Dao
public interface ScavHuntDao {
    @Query("Select * FROM ScavHunts")
    LiveData<List<ScavHunt>> getAllScavHunts();

    @Query("DELETE FROM ScavHunts")
    void deleteAllScavHunts();

    @Delete
    void delete(ScavHunt scavhunt);

    @Insert
    void insert(ScavHunt scavHunt);

    @Query("SELECT * FROM ScavItems WHERE scavHuntID = :scavHuntID")
    LiveData<List<ScavItem>> getScavItems(int scavHuntID);

    @Delete
    void delete(ScavItem scavItem);

    @Query("DELETE FROM ScavHunts")
    void deleteAllScavItems();

    @Insert
    void insert(ScavItem scavItem);
}
