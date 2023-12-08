package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static java.util.stream.Collectors.toCollection;

public class Day8_attempt2 {

    static List<String> getInput(String filename) {
        Path path = Paths.get(filename);
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        long result = 0;
        List<String> input = getInput("input_day8.txt");
//        List<String> input = getInput("input_day8_example.txt");
//        List<String> input = getInput("input_day8_example2.txt");
//        List<String> input = getInput("input_day8_example3.txt");

        Map<String, Node> nodeMap = new HashMap<>();
        Iterator<String> iterator = input.iterator();
        String directions = iterator.next();
        iterator.next(); // empty line;

        while (iterator.hasNext()) {
            var split = iterator.next().split("=");
            nodeMap.put(split[0].trim(), new Node(split[1].substring(2, 5), split[1].substring(7, 10)));
        }


        List<String> startingPoints = nodeMap.keySet().stream().filter(k -> k.endsWith("A")).collect(toCollection(ArrayList::new));

//        List<Loop> loops = new ArrayList<>();
//        for (String startingPoint : startingPoints) {
//            System.out.println(startingPoint);
//            String originalStartingPoint = startingPoint;
//            long count = 0;
//            List<Long> zs = new ArrayList<>();
//            Set<String> loopMoments = new HashSet<>();
//            int zsfound = 0;
//            long previousCount = 0;
//            while (zsfound < 5) {
//                int directionIndex = (((Long) (count % directions.length())).intValue());
//                count++;
//                char moveTo = directions.charAt(directionIndex);
//                if (moveTo == 'R') {
//                    startingPoint = nodeMap.get(startingPoint).right;
//                } else {
//                    startingPoint = nodeMap.get(startingPoint).left;
//                }
//                String moment = startingPoint + directionIndex;
//                if (loopMoments.contains(moment)) {
//                    // WE HAVE A LOOP!
//                    loops.add(new Loop(zs, directionIndex + 1, count - directionIndex));
//                    break;
//                } else {
//                    loopMoments.add(moment);
//                }

//                if (startingPoint.charAt(2) == 'Z') {
//                    zs.add(count);
//                    System.out.println(count - previousCount);
//                    previousCount = count;
//                    zsfound++;
//                }
//            }
//
//        }


        List<number> list = new ArrayList<>();
        list.add(new number(11911));
        list.add(new number(13019));
        list.add(new number(16897));
        list.add(new number(18559));
        list.add(new number(19667));
        list.add(new number(21883));

        for (int i = 0; i < Long.MAX_VALUE; i++) {
            if (list.stream().filter(l -> l.current == list.get(0).current).toList().size() == 6) {
                System.out.println(list.get(0).current);
                break;
            }
            System.out.println(list);
            list.stream().sorted(Comparator.comparing(n -> n.current)).findFirst().get().increase();
        }

        System.out.println("++++++++++++++++++ " + result + " +++++++++++++++++++");
    }

}

class number {
    long original;
    long current;

    void increase() {
        current += original;
    }

    public number(long original) {
        this.original = original;
        this.current = original;
    }


    @Override
    public String toString() {
        return "number{" +
                "original=" + original +
                ", current=" + current +
                '}';
    }
}