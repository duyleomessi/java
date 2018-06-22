package com.company;

import java.util.Random;

public class Location {
    private int[][] shipLocation;
    private int height;
    private int width;

    public Location(int height, int width) {
        this.height = height;
        this.width = width;
        shipLocation = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                this.shipLocation[i][j] = 0;
            }
        }

        createShipLocation(3);
    }

    public void createShipLocation(int lengOfShip) {
        Random randomGenerator = new Random();
        int column = randomGenerator.nextInt(this.width);
        int row = randomGenerator.nextInt(this.height);
        System.out.println("row " + (row + 1) + " column " + (column + 1));
        char base = 'A';
        int convert = base + column;
        // 1 : horizontal
        // 2: vertical
        int directionRand = randomGenerator.nextInt(2) + 1;
        if (directionRand == 1) {
            System.out.println("horizontal");
            if (column <= this.width - lengOfShip) {
                for (int i = 0; i < lengOfShip; i++) {
                    this.shipLocation[row][column + i] = 1;
                }
            } else {
                for (int i = 0; i < lengOfShip; i++) {
                    this.shipLocation[row][column - i] = 1;
                }
            }
        } else {
            System.out.println("Vertical");
            if (row <= this.height - lengOfShip) {
                for (int i = 0; i < lengOfShip; i++) {
                    this.shipLocation[row + i][column] = 1;
                }
            } else {
                for (int i = 0; i < lengOfShip; i++) {
                    this.shipLocation[row - i][column] = 1;
                }
            }
        }
    }

    public int[][] getShipLocation() {
        return this.shipLocation;
    }

    public void printShipLocation() {
        System.out.println("Ship location");
        for (int i = 0; i < shipLocation.length; i++) {
            for (int j = 0; j < shipLocation[i].length; j++) {
                System.out.print(shipLocation[i][j] + " ");
            }
            System.out.println();
        }
    }

    public boolean isFireCorrect(int[] locationConvert) {
        int i = locationConvert[0];
        int j = locationConvert[1];
        System.out.println("i " + i + " j " + j + " " + shipLocation[i][j]);
        if (shipLocation[i][j] == 1)
            return true;
        return false;
    }
}
