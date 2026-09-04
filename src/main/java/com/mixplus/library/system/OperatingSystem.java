package com.mixplus.library.system;

import oshi.SystemInfo;
import oshi.software.os.OperatingSystemVersion;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class OperatingSystem {
    private static final ScheduledExecutorService scheduler =
            Executors.newSingleThreadScheduledExecutor();

    private static final oshi.software.os.OperatingSystem os =
            new SystemInfo().getOperatingSystem();


    private OperatingSystem() {

    }

    public static void start() {
        start(0, 1, TimeUnit.SECONDS);
    }

    public static void start(long delay, long period, TimeUnit unit) {
        scheduler.scheduleAtFixedRate(
                CPU::update,
                delay,
                period,
                unit
        );
    }

    /**
     * Returns the operating system name.
     *
     * @return the operating system name
     */
    public static String getName() {
        return os.getFamily();
    }

    /**
     * Returns the operating system version.
     *
     * @return the operating system version
     */
    public static String getVersion() {
        return os.getVersionInfo().getVersion();
    }

    /**
     * Returns the operating system architecture.
     *
     * @return the operating system architecture
     */
    public static String getArchitecture() {
        return System.getProperty("os.arch");
    }

    /**
     * Returns the system uptime in seconds.
     *
     * @return the system uptime in seconds
     */
    public static Duration getUptime() {
        return Duration.ofSeconds(os.getSystemUptime());
    }

}
