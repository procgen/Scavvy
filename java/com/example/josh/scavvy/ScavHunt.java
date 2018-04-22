package com.example.josh.scavvy;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;

import io.reactivex.annotations.NonNull;

@Entity(tableName = "ScavHunts")
public class ScavHunt {

    @PrimaryKey(autoGenerate = true)
    private int shID;
    @ColumnInfo(name = "name")
    private String name;
    //private ArrayList<ScavItem> items;
    @ColumnInfo(name = "numFound")
    private Integer numFound;

    public ScavHunt(@NonNull String name, Integer numFound){
        this.name = name;
        this.numFound = numFound;
    }
    public String getName(){
        return name;
    }
    public int getShID() { return shID; }
    public Integer getNumFound() { return numFound; }
    public void setShID(int shID){this.shID = shID;}
}
