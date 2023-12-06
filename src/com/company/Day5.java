package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Day5 {

    static List<String> getInput(String filename) {
        Path path = Paths.get(filename);
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        long result = Long.MAX_VALUE;
        List<String> input = getInput("input_day5.txt");
//        List<String> input = getInput("input_day5_example.txt");

        Iterator<String> iterator = input.iterator();
        List<Long> seeds = Arrays.stream(iterator.next().substring("seeds: ".length()).split(" ")).map(Long::parseLong).toList();

        List<Mapping> seedToSoil = new ArrayList<>();
        List<Mapping> soilToFertilizer = new ArrayList<>();
        List<Mapping> fertilizerToWater = new ArrayList<>();
        List<Mapping> waterToLight = new ArrayList<>();
        List<Mapping> lightToTemp = new ArrayList<>();
        List<Mapping> tempToHumidity = new ArrayList<>();
        List<Mapping> humidityToLocation = new ArrayList<>();

        List<Mapping> currentMapping = seedToSoil;

        while (iterator.hasNext()) {
            String line = iterator.next();
            if (line.isBlank()) {
                continue;
            }

            if (line.equals("seed-to-soil map:")) {
                currentMapping = seedToSoil;
            } else if (line.equals("soil-to-fertilizer map:")) {
                currentMapping = soilToFertilizer;
            } else if (line.equals("fertilizer-to-water map:")) {
                currentMapping = fertilizerToWater;
            } else if (line.equals("water-to-light map:")) {
                currentMapping = waterToLight;
            } else if (line.equals("light-to-temperature map:")) {
                currentMapping = lightToTemp;
            } else if (line.equals("temperature-to-humidity map:")) {
                currentMapping = tempToHumidity;
            } else if (line.equals("humidity-to-location map:")) {
                currentMapping = humidityToLocation;
            } else {
                Mapping mapping = new Mapping();
                String[] split = line.split(" ");
                mapping.destinationFrom = Long.parseLong(split[0]);
                mapping.sourceFrom = Long.parseLong(split[1]);
                mapping.length = Long.parseLong(split[2]);
                currentMapping.add(mapping);
            }
        }

        for (int i = 0; i < seeds.size(); i += 2) {
            for (int j = 0; j < seeds.get(i + 1); j++) {
                long seed = seeds.get(i) + j;
                SeedResult seedResult = new SeedResult();
                seedResult.seed = seed;
                seedResult.soil = getFromMapping(seed, seedToSoil);
                seedResult.fertilizer = getFromMapping(seedResult.soil, soilToFertilizer);
                seedResult.water = getFromMapping(seedResult.fertilizer, fertilizerToWater);
                seedResult.light = getFromMapping(seedResult.water, waterToLight);
                seedResult.temp = getFromMapping(seedResult.light, lightToTemp);
                seedResult.humidity = getFromMapping(seedResult.temp, tempToHumidity);
                seedResult.location = getFromMapping(seedResult.humidity, humidityToLocation);

                if (seedResult.location < result) {
                    result = seedResult.location;
                }
            }

        }

        System.out.println("++++++++++++++++++ " + result + " +++++++++++++++++++");
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


class Mapping {
    long sourceFrom;
    long destinationFrom;
    long length;
}

class SeedResult {
    long seed;
    long soil;
    long fertilizer;
    long water;
    long light;
    long temp;
    long humidity;
    long location;
}