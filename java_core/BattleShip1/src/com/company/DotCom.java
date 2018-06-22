package com.company;

import java.util.ArrayList;

public class DotCom {
    private String name;
    private ArrayList<String> locationCells;

    public DotCom() {
    }

    public DotCom(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void printName() {
        System.out.println(this.name);
    }


    public void setLocation(ArrayList<String> locationCells) {
        this.locationCells = locationCells;
    }

    public void printLocation() {
        System.out.println(this.locationCells);
    }

    public ArrayList<String> getLocation() {
        return this.locationCells;
    }

    public String checkAnswer(String userGuess) {
        String result = "miss";

        if (locationCells.indexOf(userGuess) >= 0) {
            locationCells.remove(userGuess);
            if (locationCells.size() == 0) {
                result = "kill";
            } else if (locationCells.size() > 0) {
                result = "hit";
            }
        };
        return result;
    }
}
