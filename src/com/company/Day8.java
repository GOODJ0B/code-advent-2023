package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static java.util.stream.Collectors.toCollection;

public class Day8 {

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

        Iterator<Character> directionIterator = directions.chars().mapToObj(c -> (char) c).toList().iterator();


        List<String> nextDirections;
        nextDirections = nodeMap.keySet().stream().filter(k -> k.endsWith("A")).collect(toCollection(ArrayList::new));
        long count = 0;
        while (!nextDirections.stream().allMatch(d -> d.charAt(2) == 'Z')) {
            count++;
            char moveTo = directionIterator.next();
            if (moveTo == 'R') {
                for (int i = 0; i < nextDirections.size(); i++) {
                    nextDirections.set(i, nodeMap.get(nextDirections.get(i)).right);
                }
            } else {
                for (int i = 0; i < nextDirections.size(); i++) {
                    nextDirections.set(i, nodeMap.get(nextDirections.get(i)).left);
                }
            }

            if (!directionIterator.hasNext()) {
                directionIterator = directions.chars().mapToObj(c -> (char) c).toList().iterator();
            }
        }


        System.out.println("++++++++++++++++++ " + count + " +++++++++++++++++++");
    }

}

class Node {
    String left;
    String right;

    public Node(String left, String right) {
        this.left = left;
        this.right = right;
    }
}