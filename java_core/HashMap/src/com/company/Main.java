package com.company;

import java.util.*;

public class Main {
    private static ArrayList<String> getKeyByValue(HashMap<String, Double> team, double timeVar) {
        ArrayList<String> keyArray = new ArrayList<String>();
        Set<Map.Entry<String, Double>> entrySet = team.entrySet();
        for (Map.Entry<String, Double> entry : entrySet) {
            if (timeVar == entry.getValue()) {
                keyArray.add(entry.getKey());
            }
        }
        return keyArray;
    }

    private static HashMap<String, Double> sortByValue(HashMap<String, Double> team) {
        // convert hashmap to list of map
        List<Map.Entry<String, Double>> listTeam = new LinkedList<Map.Entry<String, Double>>(team.entrySet());

        // sort list with collection.sort(), provide a custom Comparator
        Collections.sort(listTeam, new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                return (o1.getValue().compareTo(o2.getValue()));
            }
        });

        HashMap<String, Double> sortedMap = new LinkedHashMap<String, Double>();
        for (Map.Entry<String, Double> entry: listTeam) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    private static void printMap(Map<String, Double> map) {
        Set<Map.Entry<String, Double>> entrySet = map.entrySet();
        for (Map.Entry<String, Double> entry: entrySet) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }

    public static void main(String[] args) {
        // write your code here
        HashMap<String, Double> team = new HashMap<String, Double>();

        team.put("UET FASTED", 31.02);
        team.put("Mta race", 16.05);
        team.put("Prototype", 21.0);
        team.put("Win win", 22.05);
        team.put("Sophia", 30.0);

        Set<Map.Entry<String, Double>> entrySet = team.entrySet();

        // print hash list
        for (Map.Entry<String, Double> entry : entrySet) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
        System.out.println("");


        /**************************** Add not duplicate key *********************************************/
        System.out.println("After add: ");
        team.putIfAbsent("Win win", 30.0);
        for (Map.Entry<String, Double> entry : entrySet) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
        System.out.println("");


        /**************************** SORT BY KEY *********************************************/
        ArrayList<String> keyArrayList = new ArrayList<String>();
        Set<String> keys = team.keySet();
        for (String key : keys) {
            boolean isAdd = false;
            if (keyArrayList.size() == 0) {
                keyArrayList.add(key);
            } else {
                for (String keyInArrayList : keyArrayList) {
                    int index = keyArrayList.indexOf(keyInArrayList);
                    if (key.compareTo(keyInArrayList) <= 1) {
                        keyArrayList.add(index, key);
                        isAdd = true;
                        break;
                    }
                }
                if (!isAdd) {
                    keyArrayList.add(key);
                }

            }
        }

        System.out.println("Sort by key: ");
        // get value from key
        Set<String> keySet = team.keySet();
        for (String key : keyArrayList) {
            System.out.println(key + " " + team.get(key));
        }
        System.out.println("");

        /**************************** SORT BY VALUE *********************************************/
        ArrayList<Double> valueArrayList = new ArrayList<Double>();
        Collection<Double> values = team.values();
        for (Double value : values) {
            boolean isAdd = false;
            if (valueArrayList.size() == 0) {
                valueArrayList.add(value);
            } else {
                for (Double valueInArrayList : valueArrayList) {
                    if (value <= valueInArrayList) {
                        int index = valueArrayList.indexOf(valueInArrayList);
                        valueArrayList.add(index, value);
                        isAdd = true;
                        break;
                    }
                }
                if (!isAdd) {
                    valueArrayList.add(value);
                }
            }
        }

        System.out.println("");


        System.out.println("Sort by value: ");

        LinkedHashMap<String, Double> sortedMap = new LinkedHashMap<String, Double>();
        for (Double value : valueArrayList) {
            ArrayList<String> arrayKey = getKeyByValue(team, value);
            for (String key : arrayKey) {
                sortedMap.put(key, value);
            }
        }

        Set<Map.Entry<String, Double>> entrySortedMapSet = sortedMap.entrySet();
        for (Map.Entry<String, Double> entry : entrySortedMapSet) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }


        /**************************** SORT BY VALUE *********************************************/
        HashMap<String, Double> teamSorted = sortByValue(team);
        Set<Map.Entry<String, Double>> entryTeamSet = teamSorted.entrySet();
        for (Map.Entry<String, Double> entry: entryTeamSet) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

    }
}
