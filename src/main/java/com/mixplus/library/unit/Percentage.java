package com.mixplus.library.unit;

public class Percentage {
    private Percentage() {

    }

    public static double of(long value, long total) {
        return (double) value / total * 100.0;
    }
}
