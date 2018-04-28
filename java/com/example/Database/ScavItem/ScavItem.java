package com.example.Database.ScavItem;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;

import com.example.Database.ScavHunt.ScavHunt;
//import android.arch.persistence.room.PrimaryKey;



@Entity(tableName = "ScavItems",
        primaryKeys = { "itemName", "scavHuntID" },
        foreignKeys = {
                @ForeignKey(entity = ScavHunt.class,
                        parentColumns = "shID",
                        childColumns = "scavHuntID")
        },
        indices={@Index("scavHuntID")})
public class ScavItem {
    private int scavHuntID;
    @NonNull
    private String itemName;

    @ColumnInfo(name = "found")
    private boolean found;
    public ScavItem(int scavHuntID, String itemName){
        this.scavHuntID = scavHuntID;
        this.itemName = itemName;
        found = false;
    }

    public String getItemName(){ return this.itemName; }
    public boolean getFound(){ return this.found; }
    public int getScavHuntID(){ return this.scavHuntID; }
    public void setFound(boolean found){ this.found = found; }
    //public void setItemName(@NonNull String itemName) { this.itemName = itemName; }
}
