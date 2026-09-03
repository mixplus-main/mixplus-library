package com.mixplus.library.system;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class OperatingSystem {
    private static final ScheduledExecutorService scheduler =
            Executors.newSingleThreadScheduledExecutor();


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

}
