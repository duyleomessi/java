package com.company;

import java.util.ArrayList;

public class DotComBust {
    ArrayList<DotCom> shipList = new ArrayList<DotCom>();
    Helper helper = new Helper();
    User user = new User();

    public void setUpGame() {
        DotCom dotComOne = new DotCom();
        dotComOne.setName("DuyDepTrai.com");

        DotCom dotComTwo = new DotCom();
        dotComTwo.setName("DuyHandsome.com");

        DotCom dotComThree = new DotCom("DuyBeautifull.com");
        shipList.add(dotComOne);
        shipList.add(dotComTwo);
        shipList.add(dotComThree);

        dotComOne.setLocation(helper.placeLocation(3));
        dotComTwo.setLocation(helper.placeLocation(3));
        dotComThree.setLocation(helper.placeLocation(3));

        dotComOne.printLocation();
        dotComTwo.printLocation();
        dotComThree.printLocation();
    }

    public String processAnswer(String userGuess) {
        String result = "miss";
        for (DotCom ship : shipList) {
            result = ship.checkAnswer(userGuess);
            if (result == "kill") {
                break;
            } else if (result == "hit") {
                break;
            }

        }
        return result;
    }

    public void playGame() {
        int countKillShip;
        do {
            String result = processAnswer(helper.getUserInput());
            System.out.println(result);
            if (result.equals("kill")) {
                countKillShip = user.getCountKillShip();
                countKillShip++;
                user.setCountKillShip(countKillShip);
                if (user.getCountKillShip() == 3) {
                    System.out.println("You win");
                    user.setIsWin();
                }
            }
        } while(!user.getIsWin());
    }

}

