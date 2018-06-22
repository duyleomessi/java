package com.company;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Helper {
    private int gridLength = 7;
    private int gridSize = 49;
    int comCount = 0;
    private int[] grid = new int[gridSize];
    int randomLocation;

    public ArrayList<String> placeLocation(int comSize) {
        ArrayList<String> alphaCells = new ArrayList<String>();
        int[] coord = new int[comSize];
        boolean sucess;
        comCount++;
        int incr = 1;
        if (comCount % 2 == 1) {
            incr = gridLength;
        }
        Random randomGenerator = new Random();
        String userGuess;
        do {
            sucess = true;
            randomLocation = randomGenerator.nextInt(gridSize);
            System.out.println("randomLocation " + randomLocation);
            // out of bound right
            if (incr == 1 && (randomLocation % gridLength) > (gridLength - comSize)) {
                System.out.println("Out of bound right");
                sucess = false;
            }

            // out of bounds bottom
            if (incr == gridLength && (randomLocation + gridLength * (comSize - 1)) > (gridSize - 1)) {
                System.out.println("Out of bound bottom");
                sucess = false;
            }
            if (sucess) {
                for (int i = 0; i < comSize; i++) {
                    if (grid[randomLocation + i * incr] == 1) {
                        System.out.println("Already occupy");
                        sucess = false;
                        break;
                    }
                }
            }


        } while (!sucess);

        for (int i = 0; i < comSize; i++) {
            coord[i] = randomLocation + i * incr;
        }

        int row = 0;
        int column = 0;
        char base = 'A';
        int convert = base;
        char result;
        for (int i = 0; i < comSize; i++) {
            row = (int) (coord[i] / gridLength);
            column = coord[i] % gridLength;
            convert += row;
            result = (char) convert;
            alphaCells.add("" + result + column);
            convert = base;
        }
        return alphaCells;
    }

    public String getUserInput() {
        Scanner sc = new Scanner(System.in);
        boolean rightFormat;
        char base = 'A';
        int convert = base;
        int maxChar = convert + (gridSize/gridLength);
        String userGuess;
        do {
            rightFormat = true;
            System.out.println("Enter the guess: ");
            userGuess = sc.next();
            if (userGuess.length() != 2) {
                System.out.println("Lenght is not 2");
                rightFormat = false;
            }

            if (userGuess.charAt(0) > maxChar  || userGuess.charAt(0) < 'A' ) {
                System.out.println("The charater out of bound");
                rightFormat = false;
            }

            if ( Integer.parseInt(Character.toString(userGuess.charAt(1))) < 0 || Integer.parseInt(Character.toString(userGuess.charAt(1))) > gridLength ) {
                System.out.println("The number out of bound");
                rightFormat = false;
            }
            if (!rightFormat) {
                System.out.println("Wrong format input");
            }
        } while (!rightFormat);
        return userGuess;
    }
}
