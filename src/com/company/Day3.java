package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day3 {

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
        List<String> input = getInput("input_day3.txt");

        List<Symbol> symbols = new ArrayList<>();
        for (int y = 0; y < input.size(); y++) {
            char[] chars = input.get(y).toCharArray();
            for (int x = 0; x < chars.length; x++) {
                char current = chars[x];
                if (Character.isDigit(current) || current == '.') {
                    // nothing
                } else {
                    symbols.add(new Symbol(current, new Coordinates(x, y)));
                }
            }
        }

        List<Number> numbers = new ArrayList<>();
        for (int y = 0; y < input.size(); y++) {
            char[] chars = input.get(y).toCharArray();
            String currentNumber = "";
            List<Coordinates> coordinates = new ArrayList<>();
            for (int x = 0; x < chars.length; x++) {
                char currentChar = chars[x];
                if (Character.isDigit(currentChar)) {
                    currentNumber = currentNumber + currentChar;
                    coordinates.add(new Coordinates(x, y));
                } else if (!currentNumber.isEmpty()) {
                    numbers.add(new Number(Integer.parseInt(currentNumber), coordinates));
                    currentNumber = "";
                    coordinates = new ArrayList<>();
                }
            }
            if (!currentNumber.isEmpty()) {
                numbers.add(new Number(Integer.parseInt(currentNumber), coordinates));
            }
        }

        result = symbols.stream()
                .filter(c -> c.value == '*')
                .map(symbol ->
                    numbers.stream().filter(number -> isAdjacent(number, symbol.coordinates)).toList()
                )
                .filter(l -> l.size() == 2)
                .mapToInt(l -> l.get(0).value * l.get(1).value)
                .sum();

        System.out.println("++++++++++++++++++ " + result + " +++++++++++++++++++");
    }

    static boolean isAdjacent(Number number, Coordinates symbol) {
        return number.coordinates.stream().anyMatch(c -> isPlusOrMinus1TheSame(c.x, symbol.x) && isPlusOrMinus1TheSame(c.y, symbol.y));
    }

    static boolean isPlusOrMinus1TheSame(int a, int b) {
        return a - b <= 1 && a-b >= -1;
    }

    static record Coordinates(int x, int y) {
    }

    static record Number(int value, List<Coordinates> coordinates) {
    }
    static record Symbol(char value, Coordinates coordinates) {
    }
}
