package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day4 {

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
        List<String> input = getInput("input_day4.txt");
//        List<String> input = getInput("input_day4_example.txt");

        List<Card> cards = new ArrayList<>();
        for (String line : input) {
            List<Integer> mine = new ArrayList<>();
            List<Integer> winning = new ArrayList<>();

            int firstIndex = 10;
            int winnersCount = 10;
            int minersCount = 25;
//            int firstIndex = 8;
//            int winnersCount = 5;
//            int minersCount = 8;

            for (int i = firstIndex; i < firstIndex + winnersCount * 3; i+=3) {
                int winner = Integer.parseInt(line.substring(i, i + 2).trim());
                System.out.println("winner: " + winner);
                winning.add(winner);
            }
            int firstMinderIndex = firstIndex + winnersCount * 3 + 2;
            for (int i = firstMinderIndex; i < firstMinderIndex + minersCount * 3; i+=3) {
                int miner = Integer.parseInt(line.substring(i, i + 2).trim());
                System.out.println("miner: " + miner);
                mine.add(miner);
            }
            long winningCount = mine.stream().filter(m -> winning.stream().anyMatch(w -> w.equals(m))).count();
            cards.add(new Card(winningCount, 1));
        }

        for (int i = 0; i < cards.size(); i++) {
            for (int j = 0; j < cards.get(i).copies; j++) {
                for (int k = 1; k < cards.get(i).winnersCount + 1; k++) {
                    cards.get(i + k).copies += 1;
                }
            }
        }

        result = cards.stream().mapToInt(c -> c.copies).sum();
        System.out.println("++++++++++++++++++ " + result + " +++++++++++++++++++");
    }

}

class Card{
    long winnersCount;
    int copies;

    public Card(long winnersCount, int copies) {
        this.winnersCount = winnersCount;
        this.copies = copies;
    }
}
