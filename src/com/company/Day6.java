package com.company;

import java.util.List;

public class Day6 {

    static List<Race> races = List.of(
            new Race(44826981, 202107611381458L)
    );
    static List<Race> racesExample = List.of(
            new Race(71530, 940200)
    );

    public static void main(String[] args) {
        long result = 1;
//        List<Race> input = racesExample;
        List<Race> input = races;

        for (Race race : input) {
            int count = 0;
            for (int i = 0; i < race.time(); i++) {
                if (wins(race, i)) {
                    count++;
                }
            }
            if (count > 0) {
                result *= count;
            }
        }


        System.out.println("++++++++++++++++++ " + result + " +++++++++++++++++++");
    }

    private static boolean wins(Race race, int buttonPressTime) {
        return (race.time() - buttonPressTime) * buttonPressTime > race.record();
    }

    public static long getFromMapping(long source, List<Mapping> mappings) {
        for (Mapping mapping : mappings) {
            if (mapping.sourceFrom <= source && mapping.sourceFrom + mapping.length > source) {
                // This mapping contains the source!
                long delta = source - mapping.sourceFrom;
                return mapping.destinationFrom + delta;
            }
        }
        return source;
    }
}

record Race(long time, long record) {
}
