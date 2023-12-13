package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Day13 {

    public static void main(String[] args) {
        new Day13().start();
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
//        List<String> input = getInput("input_day13.txt");
        List<String> input = getInput("input_day13_example.txt");


        System.out.println("++++++++++++++++++ " + result + " +++++++++++++++++++");
    }


}
