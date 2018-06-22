// cccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc
package com.company;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Main {

    private static boolean isContain(String s) {
        return !s.matches("[ces]{1440}");
    }

    private static int longestCodeStreakInDay(String s) {
        int max = 0;
        String longest = "";
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'c') {
                longest += s.charAt(i);
            }
            if ((i == s.length() - 1) || s.charAt(i) != 'c') {
                // System.out.println(longest + " " + longest.length());
                max = longest.length() > max ? longest.length() : max;
                longest = "";
            }
        }
        return max;
    }

    private static int getMaxOfMaxInSingleDay(List<Integer> maxInDay) {
        Collections.sort(maxInDay);
        return maxInDay.get(maxInDay.size() - 1);
    }

    private static int longestCodeStreakInPeriod(String[] s, int maxOfMaxInSingleDay) {
        int max = 0;
        ArrayList<ArrayList<Integer>> listOfList = new ArrayList<ArrayList<Integer>>();


        for (int i = 0; i < s.length - 1; i++) {
            // System.out.println(s[i].charAt(s.length - 1) + " " +  s[i + 1].charAt(0) );
            ArrayList<Integer> singleList = new ArrayList<Integer>();
            if (s[i].charAt(s[i].length() - 1) == 'c' && s[i + 1].charAt(0) == 'c') {
                if (singleList.size() == 0) {
                    // System.out.println("size 0");
                    singleList.add(i);
                    singleList.add(i + 1);
                } else {
                    // System.out.println("size not 0");
                    singleList.set(0, i);
                    singleList.set(1, i + 1);
                }
                listOfList.add(singleList);
            }
        }

        for (int i = 0; i < listOfList.size(); i++) {
            int firstElement = listOfList.get(i).get(0);
            int sencondElement = listOfList.get(i).get(1);
            String longest = "";
            for (int j = s[firstElement].length() - 1; j >= 0; j--) {
                if (s[firstElement].charAt(j) == 'c') {
                    longest += "c";
                }
                else {
                    break;
                }
            }
            for (int j = 0; j < s[sencondElement].length(); j++) {
                if (s[sencondElement].charAt(j) == 'c') {
                    longest += "c";
                } else {
                    break;
                }
            }
            // System.out.println("longest " + longest);

            maxOfMaxInSingleDay = (longest.length() > maxOfMaxInSingleDay) ? longest.length() : maxOfMaxInSingleDay;
        }

        return maxOfMaxInSingleDay;
    }


    public static void main(String[] args) throws Exception {
        // write your code here
        int numberOfDay;
        String[] stringActive;
        // enter the number of day

        do {
            // System.out.print("Enter the number of days: ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            numberOfDay = Integer.parseInt(br.readLine());
        } while (numberOfDay < 1 || numberOfDay > 365);

        stringActive = new String[numberOfDay];
        for (int i = 0; i < numberOfDay; i++) {
            do {
                stringActive[i] = "";
                // System.out.println("Day " + (i + 1));
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                stringActive[i] += br.readLine();
            } while (isContain(stringActive[i]));
        }
        System.out.println(stringActive[0]);

        List<Integer> maxInDay = new ArrayList<Integer>();
        for (int i = 0; i < numberOfDay; i++) {
            int max = longestCodeStreakInDay(stringActive[i]);
            // System.out.println("Longest coding time in day " + (i + 1) + " " + max);
            maxInDay.add(max);
        }

        int maxOfMaxInSingleDay = getMaxOfMaxInSingleDay(maxInDay);
        int maxOfMax = longestCodeStreakInPeriod(stringActive, maxOfMaxInSingleDay);
        // System.out.println("Longest coding time " + maxOfMax);

        System.out.println(getMaxOfMaxInSingleDay(maxInDay) + " " + maxOfMax);
    }
}
