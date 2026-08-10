package com.mixplus.library.system;



import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;
import unit.DataUnit;
import unit.Percentage;


/**
 * Provides information about the system's physical memory.
 *
 * <p>Memory sizes returned by this class are measured in bytes by default.
 * When a {@link DataUnit} is specified, the value is converted using
 * a base of 1024.</p>
 *
 *
 * <p>Memory usage is calculated from the used and total physical memory
 * and returned as a percentage.</p>
 */
public final class Memory {
    private static final GlobalMemory MEMORY =
            new SystemInfo().getHardware().getMemory();

    private Memory() {

    }

    /**
     * Returns the total amount of physical memory in bytes.
     *
     * @return total physical memory in bytes
     */
    public static long getTotal() {
        return MEMORY.getTotal();
    }

    /**
     * Returns the total amount of physical memory in the specified unit.
     *
     * @param unit the unit to convert the memory size to
     * @return total physical memory in the specified unit
     */
    public static long getTotal(DataUnit unit) {
        return unit.fromBytes(Memory.getTotal());
    }


    /**
     * Returns the amount of physical memory currently available to the system
     * in bytes.
     *
     * @return available physical memory in bytes
     */
    public static long getAvailable() {
        return MEMORY.getAvailable();
    }

    /**
     * Returns the amount of physical memory currently available to the system
     * in the specified unit.
     *
     * @param unit the unit to convert the memory size to
     * @return available physical memory in the specified unit
     */
    public static long getAvailable(DataUnit unit) {
        return unit.fromBytes(Memory.getAvailable());
    }

    /**
     * Returns the amount of physical memory currently in use by the system
     * in bytes.
     *
     * <p>The used memory is calculated as total memory minus available memory.</p>
     *
     * @return used physical memory in bytes
     */
    public static long getUsed() {
        return getTotal() - getAvailable();
    }

    /**
     * Returns the amount of physical memory currently in use by the system
     * in the specified unit.
     *
     * @param unit the unit to convert the memory size to
     * @return used physical memory in the specified unit
     */
    public static long getUsed(DataUnit unit) {
        return unit.fromBytes(getUsed());
    }

    /**
     * Returns the percentage of physical memory currently in use.
     * @return memory usage as a percentage from {@code 0.0} to {@code 100.0}
     */
    public static double getUsage() {
        return Percentage.of(getUsed(), getTotal());
    }
}
