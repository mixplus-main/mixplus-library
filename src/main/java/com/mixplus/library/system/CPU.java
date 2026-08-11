package com.mixplus.library.system;



import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import com.mixplus.library.unit.*;


public final class CPU {

    private static final CentralProcessor PROCESSOR =
            new SystemInfo().getHardware().getProcessor();

    private static long[] ticks =
            PROCESSOR.getSystemCpuLoadTicks();

    private static volatile double usage;

    private CPU() {
    }

    /**
     * Updates the system-wide CPU usage measurement.
     *
     * <p>The first call may return a CPU usage of {@code 0.0} because
     * there is no previous measurement interval available yet.</p>
     * @since 1.0.1
     */
    public static void update() {
        long[] currentTicks = PROCESSOR.getSystemCpuLoadTicks();

        usage = PROCESSOR.getSystemCpuLoadBetweenTicks(ticks) * 100.0;

        ticks = currentTicks;
    }

    /**
     * Returns the latest measured system-wide CPU usage.
     *
     * <p>The initial value is {@code 0.0} until {@link #update()} has
     * been called with a sufficient interval since the initial measurement.</p>
     *
     * @return the latest CPU usage as a percentage, from {@code 0.0} to {@code 100.0}
     * @since 1.0.1
     */
    public static double getUsage() {
        return usage;
    }

    /**
     * Returns the name of the CPU.
     *
     * @return the CPU name
     * @since 1.1.1
     */
    public static String getName() {
        return PROCESSOR.getProcessorIdentifier().getName();
    }

    /**
     * Returns the number of physical CPU cores.
     *
     * @return the number of physical CPU cores
     * @since 1.1.3
     */
    public static int getPhysicalProcessorCount() {
        return PROCESSOR.getPhysicalProcessorCount();
    }

    /**
     * Returns the number of logical processors.
     *
     * <p>The logical processor count includes threads provided by
     * technologies such as simultaneous multithreading (SMT).</p>
     *
     * @return the number of logical processors
     * @since 1.1.3
     */
    public static int getLogicalProcessorCount() {
        return PROCESSOR.getLogicalProcessorCount();
    }

    /**
     * Returns the maximum CPU frequency reported by the operating system.
     *
     * @return the maximum CPU frequency in hertz, or {@code -1}
     *         if the frequency cannot be determined
     */
    public static long getMaxFrequency() {
        return PROCESSOR.getMaxFreq();
    }
}
