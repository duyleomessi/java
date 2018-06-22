package com.company;

public class User {
    private boolean isWin;
    private int countGuess;
    private int countKillShip;

    public User() {
        this.countGuess = 0;
        this.isWin = false;
        this.countKillShip = 0;
    }

    public int getCountGuess() {
        return countGuess;
    }

    public boolean getIsWin() {
        return isWin;
    }

    public void setIsWin() {
        this.isWin = true;
    }

    public int getCountKillShip() {
        return countKillShip;
    }

    public void setCountKillShip(int countKillShip) {
        this.countKillShip = countKillShip;
    }
}
