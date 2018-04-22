package com.example.josh.scavvy;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ScavHuntDao {
    @Query("Select * FROM ScavHunts")
    LiveData<List<ScavHunt>> getAll();

    @Delete
    void delete(ScavHunt scavhunt);

    @Insert
    void insert(ScavHunt scavHunt);
}
