package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Day9 {

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
        List<String> input = getInput("input_day9.txt");
//        List<String> input = getInput("input_day9_example.txt");

        List<List<Long>> lists = input.stream().map(line -> Arrays.stream(line.split(" ")).map(Long::parseLong).toList()).toList();

        for (List<Long> list : lists) {
            ArrayList<Long> newList = new ArrayList<>(list);
            Collections.reverse(newList);
            result += getNextValue(newList);
        }
        System.out.println("++++++++++++++++++ " + result + " +++++++++++++++++++");
    }

    public static long getNextValue(List<Long> list) {
        if (list.stream().allMatch(i -> i == 0)) {
            return 0;
        }
        List<Long> newList = new ArrayList<>();
        for (int i = 0; i < list.size() - 1; i++) {
            newList.add(list.get(i + 1) - list.get(i));
        }
        return list.get(list.size() - 1) + getNextValue(newList);
    }

}
