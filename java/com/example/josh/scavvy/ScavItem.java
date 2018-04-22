package com.example.josh.scavvy;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "ScavItems",
        primaryKeys = { "scavHuntID", "name" },
        foreignKeys = {
                @ForeignKey(entity = ScavHunt.class,
                        parentColumns = "shID",
                        childColumns = "scavHuntID")
        })
public class ScavItem {
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "found")
    private boolean found;

    public ScavItem(String name){
        this.name = name;
        found = false;
    }

    public String getName(){ return this.name; }
    public boolean getFound(){ return this.found; }
}
