package com.company;

public class LCMCalculator {
    // Function to calculate GCD using the Euclidean algorithm
    private static long calculateGCD(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    // Function to calculate LCM
    private static long calculateLCM(long a, long b) {
        return (a * b) / calculateGCD(a, b);
    }

    // Function to calculate LCM for an array of numbers
    private static long calculateLCMForArray(long[] numbers) {
        long lcm = numbers[0];
        for (int i = 1; i < numbers.length; i++) {
            lcm = calculateLCM(lcm, numbers[i]);
        }
        return lcm;
    }

    public static void main(String[] args) {
        long[] numbers = {2000, 3000, 4000};

        long lcm = calculateLCMForArray(numbers);

        System.out.println("The number divisible by all six numbers is: " + lcm);
    }
}