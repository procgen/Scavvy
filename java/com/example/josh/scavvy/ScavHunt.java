package com.example.josh.scavvy;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

import android.support.annotation.NonNull;

@Entity(tableName = "ScavHunts")
public class ScavHunt {

    @PrimaryKey(autoGenerate = true)
    private int shID;
    @ColumnInfo(name = "name")
    @NonNull private String name;
    //private ArrayList<ScavItem> items;
    @ColumnInfo(name = "numFound")
    private int numFound;
    /*@Ignore
    private List<ScavItem> scavItems;*/

    public ScavHunt(@NonNull String name, int numFound){
        this.name = name;
        this.numFound = numFound;
    }
    public String getName(){
        return name;
    }
    public int getShID() { return shID; }
    public int getNumFound() { return numFound; }
    public void setShID(int shID){this.shID = shID;}
    /*public List<ScavItem> getScavItems() {
        return scavItems;
    }

    public void setScavItems(List<ScavItem> scavItems) {
        this.scavItems = scavItems;
    }*/
}
