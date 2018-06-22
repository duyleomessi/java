package com.company;

import java.util.Random;

public class Map {
    private int heigh;
    private int width;
    private String[][] map;
    private Location shipLocation;

    public Map() {

    }

    public Map(int heigh, int width) {
        this.heigh = heigh;
        this.width = width;
        this.shipLocation = new Location(heigh, width);
        this.map = new String[this.heigh][];
        this.map = this.createMap();
    }

    public String[][] createMap() {
        map = new String[this.heigh][this.width];
        char base = 'A';

        for (int i = 0; i < this.heigh; i++) {
            int convert = base;
            convert += i;
            // System.out.println(convert);
            for(int j = 0; j < this.width; j++) {
                // base = (char)convert;
                // System.out.println("" + (char)convert + j);
                map[i][j] = "" + (char)convert + (j+1);
            }

        }
        return map;
    }

    public void printMap() {
        if (this.map != null) {
            System.out.println("The map");
            for (int i = 0; i < this.map.length; i++) {
                for (int j = 0; j < this.map[i].length; j++) {
                    System.out.print(this.map[i][j] + " ");
                }
                System.out.println();
            }
        } else {
            System.out.println("The map is not created");
        }
    }

    public Location getShipLocation() {
        return this.shipLocation;
    }

    public int[] convertAnswerToLocation(String userGuess) {
        int[] location = new int[2];
        for (int i = 0; i < this.map.length; i++) {
            for(int j = 0; j < this.map[i].length; j++) {
                if (map[i][j].equals(userGuess)) {
                    location[0] = i;
                    location[1] = j;
                }
            }
        }
        return location;
    }
}
