package com.example.josh.scavvy;

import java.util.ArrayList;

public class ScavHunt {
    private String name;
    private ArrayList<ScavItem> items;
    private Integer numFound;

    public ScavHunt(String name, ArrayList<ScavItem> items, Integer numFound){
        this.name = name;
        this.items = items;
        this.numFound = numFound;
    }
    public String getName(){
        return name;
    }
}
