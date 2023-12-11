package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day11 {
    List<List<String>> grid;

    public static void main(String[] args) {
        new Day11().start();
    }

    static List<String> getInput(String filename) {
        Path path = Paths.get(filename);
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void start() {
        long result = 0;
        List<String> input = getInput("input_day11.txt");
//        List<String> input = getInput("input_day11_example.txt");

        String expansion = "1000000";
        grid = new ArrayList<>();
        for (String line : input) {
            List<String> row = stringToList(line);
            // row expansion:
            if (row.stream().allMatch(s -> s.equals("."))) {
                grid.add(row.stream().map(i -> expansion).collect(Collectors.toCollection(ArrayList::new)));
            } else {
                grid.add(row);
            }
        }

        List<Integer> indexesOfEmptyColumns = new ArrayList<>();
        for (int i = grid.get(0).size() - 1; i >= 0; i--) {
            int finalI = i;
            if (grid.stream().allMatch(row -> row.get(finalI).equals(".") || row.get(finalI).equals(expansion))) {
                indexesOfEmptyColumns.add(i);
            }
        }
        for (Integer index : indexesOfEmptyColumns) {
            for (List<String> row : grid) {
                row.set(index, expansion);
            }
        }

        printGrid();

        List<Coordinates> galaxies = new ArrayList<>();
        for (int y = 0; y < grid.size(); y++) {
            for (int x = 0; x < grid.get(y).size(); x++) {
                if (grid.get(y).get(x).equals("#")) {
                    galaxies.add(new Coordinates(x, y));
                }
            }
        }

        for (int y = 0; y < grid.size(); y++) {
            for (int x = 0; x < grid.get(y).size(); x++) {
                if (grid.get(y).get(x).equals(".") || grid.get(y).get(x).equals("#")) {
                    grid.get(y).set(x, "1");
                }
            }
        }

        for (int i = 0; i < galaxies.size(); i++) {
            for (int j = i + 1; j < galaxies.size(); j++) {
                result += distanceBetween(galaxies.get(i), galaxies.get(j));
            }
        }


        System.out.println("++++++++++++++++++ " + result + " +++++++++++++++++++");
    }

    public void printGrid() {
        for (List<String> row : grid) {
            for (String character : row) {
                System.out.print(character + "");
            }
            System.out.println();
        }
    }

    public static List<String> stringToList(String inputString) {
        List<String> characterList = new ArrayList<>();

        // Iterate through each character in the string
        for (int i = 0; i < inputString.length(); i++) {
            // Convert each character to a string and add it to the list
            characterList.add(Character.toString(inputString.charAt(i)));
        }

        return characterList;
    }

    int distanceBetween(Coordinates a, Coordinates b) {
        int distanceX = 0;
        int distanceY = 0;

        int currentX = a.x();
        while (currentX != b.x()) {
            if (a.x() < b.x()) {
                currentX++;
            } else {
                currentX--;
            }
            distanceX += Integer.parseInt(grid.get(a.y()).get(currentX));
        }


        int currentY = a.y();
        while (currentY != b.y()) {
            if (a.y() < b.y()) {
                currentY++;
            } else {
                currentY--;
            }
            distanceY += Integer.parseInt(grid.get(currentY).get(currentX));
        }


        return Math.abs(distanceX) + Math.abs(distanceY);
    }

}

record Coordinates(int x, int y) {
}