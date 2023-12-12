package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Day12 {

    static AtomicLong result = new AtomicLong(0);
    static AtomicInteger count = new AtomicInteger(0);
    static Map<String, Long> cache = new HashMap<>();

    public static void main(String[] args) {
        new Day12().start();
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
        List<String> input = getInput("input_day12.txt");
//        List<String> input = getInput("input_day12_example.txt");
        int multiplication = 5;
        long startGlobal = System.currentTimeMillis();

        input.stream().forEach(s -> {
                    long startLocal = System.currentTimeMillis();
                    String[] split = s.split(" ");
                    String characters = "";
                    for (int i = 0; i < multiplication; i++) {
                        characters += split[0];
                        if (i != multiplication - 1) {
                            characters += "?";
                        }
                    }
                    List<Integer> numbers = new ArrayList<>();
                    for (int i = 0; i < multiplication; i++) {
                        for (String c : split[1].split(",")) {
                            numbers.add(Integer.parseInt(c));
                        }
                    }
                    long r = countConfigurations(characters, numbers);
                    result.getAndAdd(r);
                    System.out.println("Done in " + ((System.currentTimeMillis() - startLocal) / 1000) + " seconden: " + r + " - " + s + ". Nog te gaan: " + (input.size() - count.incrementAndGet()) + " (" + ((System.currentTimeMillis() - startGlobal) / 1000) + " seconden bezig, " + ((System.currentTimeMillis() - startGlobal) / 1000.0 / count.get() * (1000 - count.get())) + " te gaan )");
                }
        );
        System.out.println("Klaar in ms: " + ((System.currentTimeMillis() - startGlobal)));
        System.out.println("++++++++++++++++++ " + result + " +++++++++++++++++++");
    }


    private long countConfigurations(String currentCode, List<Integer> numbers) {
        if (cache.containsKey(currentCode + numbers)) {
            return cache.get(currentCode + numbers);
        }
        if (currentCode.isBlank()) {
            return numbers.isEmpty() ? 1 : 0;
        }
        long count;

        char first = currentCode.charAt(0);
        if (first == '.') {
            count = countConfigurations(currentCode.substring(1), numbers);
        } else if (first == '?') {
            count = countConfigurations("#" + currentCode.substring(1), numbers)
                    + countConfigurations("." + currentCode.substring(1), numbers);
        } else {
            if (numbers.isEmpty()) {
                count = 0;
            } else {
                int currentGroupLength = numbers.get(0);
                if (currentGroupLength > currentCode.length() || currentCode.substring(0, currentGroupLength).contains(".")) {
                    count = 0;
                } else {
                    List<Integer> newNumbers = numbers.subList(1, numbers.size());
                    if (currentGroupLength == currentCode.length()) {
                        count = countConfigurations("", newNumbers);
                    } else if (currentCode.charAt(currentGroupLength) == '?' || currentCode.charAt(currentGroupLength) == '.') {
                        count = countConfigurations(currentCode.substring(currentGroupLength + 1), newNumbers);
                    } else {
                        count = 0;
                    }
                }
            }
        }

        cache.put(currentCode + numbers, count);
        return count;
    }


}
