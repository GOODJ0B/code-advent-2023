package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Day10 {

    String[][] grid;

    static List<String> getInput(String filename) {
        Path path = Paths.get(filename);
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        new Day10().start();
    }

    public void start() {
        long result = 0;
        List<String> input = getInput("input_day10.txt");
//        List<String> input = getInput("input_day10_example.txt");

        grid = new String[input.size() * 2][input.get(0).length() * 2];
        for (int i = 0; i < input.size() * 2; i += 2) {
            grid[i] = stringToList(input.get(i / 2));
            grid[i + 1] = stringToList("+".repeat(input.get(0).length()));
        }

        int startX = 0;
        int startY = 0;
        outer:
        for (int x = 0; x < grid[0].length; x++) {
            for (int y = 0; y < grid.length; y++) {
                if (grid[y][x].equals("S")) {
                    startX = x;
                    startY = y;
                    break outer;
                }
            }
        }

        try {
            move(Direction.right, startX, startY, 0);
        } catch (Exception e) {
            System.out.println(e);
        }

        markOutside(0, 0);

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println(); // Move to the next line after each row
        }

        result = Arrays.stream(grid).mapToLong(line -> Arrays.stream(line).filter(s -> !s.equals("!") && !s.equals("0") && !s.equals("+")).count()).sum();

        System.out.println("++++++++++++++++++ " + result + " +++++++++++++++++++");
    }

    private void markOutside(int x, int y) {
        try {
            if (grid[y][x].equals("!") || grid[y][x].equals("0")) {
                // do nothing
            } else {
                grid[y][x] = "0";
                markOutside(x - 1, y);
                markOutside(x + 1, y);
                markOutside(x, y - 1);
                markOutside(x, y + 1);
            }
        } catch (Exception e) {
            // do nothing
        }
    }

    public void move(Direction direction, int fromX, int fromY, long count) {
//        System.out.println(grid[fromY][fromX] + " - " + count);

        grid[fromY][fromX] = "!";

        int toX = switch (direction) {
            case left -> fromX - 1;
            case up -> fromX;
            case down -> fromX;
            case right -> fromX + 1;
        };
        int toY = switch (direction) {
            case left -> fromY;
            case up -> fromY - 1;
            case down -> fromY + 1;
            case right -> fromY;
        };

        switch (grid[toY][toX]) {
            case "+" -> {
                switch (direction) {
                    case up -> move(Direction.up, toX, toY, count + 1);
                    case down -> move(Direction.down, toX, toY, count + 1);
                    case left -> move(Direction.left, toX, toY, count + 1);
                    case right -> move(Direction.right, toX, toY, count + 1);
                }
            }
            case "|" -> {
                switch (direction) {
                    case up -> move(Direction.up, toX, toY, count + 1);
                    case down -> move(Direction.down, toX, toY, count + 1);
                    default -> System.out.println("Impossible move.");
                }
            }
            case "-" -> {
                switch (direction) {
                    case right -> move(Direction.right, toX, toY, count + 1);
                    case left -> move(Direction.left, toX, toY, count + 1);
                    default -> System.out.println("Impossible move.");
                }
            }
            case "L" -> {
                switch (direction) {
                    case down -> move(Direction.right, toX, toY, count + 1);
                    case left -> move(Direction.up, toX, toY, count + 1);
                    default -> System.out.println("Impossible move.");
                }
            }
            case "J" -> {
                switch (direction) {
                    case down -> move(Direction.left, toX, toY, count + 1);
                    case right -> move(Direction.up, toX, toY, count + 1);
                    default -> System.out.println("Impossible move.");
                }
            }
            case "7" -> {
                switch (direction) {
                    case right -> move(Direction.down, toX, toY, count + 1);
                    case up -> move(Direction.left, toX, toY, count + 1);
                    default -> System.out.println("Impossible move.");
                }
            }
            case "F" -> {
                switch (direction) {
                    case up -> move(Direction.right, toX, toY, count + 1);
                    case left -> move(Direction.down, toX, toY, count + 1);
                    default -> System.out.println("Impossible move.");
                }
            }
            case "." -> {
                System.out.println("reached a dot.");
            }
            case "S" -> {
                System.out.println("Reached the end! " + (count + 1) / 2);
            }
        }
    }

    public static String[] stringToList(String inputString) {
        String[] characterList = new String[inputString.length() * 2];

        // Iterate through each character in the string
        for (int i = 0; i < inputString.length() * 2; i += 2) {
            // Convert each character to a string and add it to the list
            characterList[i] = (Character.toString(inputString.charAt(i / 2)));
            characterList[i + 1] = "+";
        }

        return characterList;
    }

}

enum Direction {
    left, right, up, down;
}

