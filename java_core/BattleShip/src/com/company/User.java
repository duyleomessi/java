package com.company;

public class User {
    private double score;
    private int numberShip;
    private int numberGuess;

    public User() {
        this.score = 0;
        this.numberGuess = 0;
        this.numberShip = 0;
    }

    public int getNumberGuess() {
        return this.numberGuess;
    }

    public int getNumberShip() {
        return this.numberShip;
    }

    public double getScore() {
        System.out.println("number of Guess: " + this.numberGuess);
        return this.score = (double) ((10 * this.numberShip) / this.numberGuess);
    }

    public void setNumberGuess(int numberGuess) {
        this.numberGuess++;
    }

    public void setNumberShip(int numberShip) {
        this.numberShip = numberShip;
    }
}
