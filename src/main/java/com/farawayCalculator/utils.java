package com.farawayCalculator;

public class utils {

    // method returns a minimum of four integers
    public static int min4(int a, int b, int c, int d) {
        return Math.min(Math.min(a, b), Math.min(c, d));
    }

    public static int min3(int a, int b, int c) {
        return Math.min(Math.min(a, b), c);
    }

}
