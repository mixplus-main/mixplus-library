package com.mixplus.library.unit;

public class Percentage {
    private Percentage() {

    }

    public static double of(long value, long total) {
        if (total == 0) {
            return 0.0;
        }

        return (double) value / total * 100.0;
    }
}
