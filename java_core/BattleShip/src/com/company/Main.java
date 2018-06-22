package com.company;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        // write your code here
        User user = new User();
        boolean isWin;
        Map map = new Map(7, 7);
        map.printMap();
        map.getShipLocation().printShipLocation();
        int numberGuess = 0;
        Scanner sc = new Scanner(System.in);
        do {
            numberGuess++;
            user.setNumberGuess(numberGuess);
            user.setNumberShip(1);
            System.out.print("Enter you guess: ");
            String userGuess = sc.next();
            int[] locationConvert = map.convertAnswerToLocation(userGuess);
            isWin = map.getShipLocation().isFireCorrect(locationConvert);
            if (isWin) {
                System.out.println("You win");
                System.out.println("You get " + user.getScore() + " mark");
            }
        } while (!isWin);
    }
}