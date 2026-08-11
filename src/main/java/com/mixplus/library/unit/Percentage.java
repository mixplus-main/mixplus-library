package com.mixplus.library.unit;


/**
 * Provides utility methods for calculating percentages.
 *
 * <p>This class cannot be instantiated.</p>
 */
public final class Percentage {
    private Percentage() {

    }

    /**
     * Calculates the percentage of a value relative to a total.
     *
     * <p>The calculation is performed as:</p>
     *
     * <pre>{@code
     * value / total * 100
     * }</pre>
     *
     * <p>If {@code total} is {@code 0}, this method returns {@code 0.0}
     * instead of performing a division by zero.</p>
     *
     * @param value the current value
     * @param total the total value
     * @return the percentage represented by {@code value}, or {@code 0.0}
     *         if {@code total} is {@code 0}
     */
    public static double of(long value, long total) {
        if (total == 0) {
            return 0.0;
        }

        return (double) value / total * 100.0;
    }
}
