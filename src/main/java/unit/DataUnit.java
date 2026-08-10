package unit;


/**
 * Units for representing data sizes.
 *
 * <p>All units use a base of 1024.</p>
 */
public enum DataUnit {
    BYTE(1),
    KB(1024),
    MB(1024L * 1024),
    GB(1024L * 1024 * 1024),
    TB(1024L * 1024 * 1024 * 1024);

    private final long bytes;

    DataUnit(long bytes) {
        this.bytes = bytes;
    }

    public long fromBytes(long value) {
        return value / bytes;
    }
}
