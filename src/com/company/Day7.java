package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Day7 {

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
        List<String> input = getInput("input_day7.txt");
//        List<String> input = getInput("input_day7_example.txt");

        List<Line> lines = input.stream().map(l -> {
            var split = l.split(" ");
            return new Line(new Hand(split[0]), Long.parseLong(split[1]));
        }).toList();

        List<Line> sortedLines = lines.stream().sorted(Comparator.comparing(Line::hand)).toList();

        for (int i = 0; i < sortedLines.size(); i++) {
            Line line = sortedLines.get(i);
            int rank = i + 1;
            result += rank * line.bidAmount();
        }

        System.out.println("++++++++++++++++++ " + result + " +++++++++++++++++++");
    }

}

class Hand implements Comparable<Hand> {
    String cards;
    Type type;

    private List<Character> cardOrder = List.of('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J');

    public Hand(String cards) {
        this.cards = cards;
        this.type = getType(cards);
    }

    private Type getType(String cards) {
        List<CardCount> cardCounts = new ArrayList<>();

        for (int i : cards.chars().toArray()) {
            Optional<CardCount> cardCount = cardCounts.stream().filter(cc -> cc.card == i).findFirst();
            if (cardCount.isPresent()) {
                cardCount.get().count = cardCount.get().count + 1;
            } else {
                cardCounts.add(new CardCount((char) i));
            }
        }

        cardCounts.sort(Comparator.comparing(cc -> 10 - cc.count));

        Optional<CardCount> jokers = cardCounts.stream().filter(cc -> cc.card == 'J').findFirst();
        if (jokers.isPresent() && jokers.get().count != 5) {
            if (cardCounts.get(0).card != 'J') {
                cardCounts.get(0).count = cardCounts.get(0).count + jokers.get().count;
            } else {
                cardCounts.get(1).count = cardCounts.get(1).count + jokers.get().count;
            }
            jokers.get().count = 0;
        }

        cardCounts.sort(Comparator.comparing(cc -> 10 - cc.count));

        if (cardCounts.get(0).count == 5) {
            return Type.FiveOfAKind;
        } else if (cardCounts.get(0).count == 4) {
            return Type.FourOfAKind;
        } else if (cardCounts.get(0).count == 3) {
            if (cardCounts.get(1).count == 2) {
                return Type.FullHouse;
            } else {
                return Type.ThreeOfAKind;
            }
        } else if (cardCounts.get(0).count == 2) {
            if (cardCounts.get(1).count == 2) {
                return Type.TwoPair;
            } else {
                return Type.OnePair;
            }
        }
        return Type.HighCard;
    }

    @Override
    public int compareTo(Hand o) {
        int basedOnType = o.type.compareTo(type);
        if (basedOnType != 0) {
            return basedOnType;
        }
        return cardsCompareTo(o.cards, cards);
    }

    public int cardsCompareTo(String a, String b) {
        for (int i = 0; i < a.length(); i++) {
            int result = cardCompareTo(a.charAt(i), b.charAt(i));
            if (result != 0) {
                return result;
            }
        }
        return 0;
    }

    public int cardCompareTo(Character a, Character b) {
        return cardOrder.indexOf(a) - cardOrder.indexOf(b);
    }

    @Override
    public String toString() {
        return "Hand{" +
                "cards='" + cards + '\'' +
                ", type=" + type +
                '}';
    }
}

final class CardCount {
    char card;
    int count = 1;

    public CardCount(char card) {
        this.card = card;
    }
}

record Line(Hand hand, long bidAmount) {
}

enum Type {
    FiveOfAKind, FourOfAKind, FullHouse, ThreeOfAKind, TwoPair, OnePair, HighCard;


}