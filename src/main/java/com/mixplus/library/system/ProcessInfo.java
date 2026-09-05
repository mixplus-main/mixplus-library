package com.mixplus.library.system;

public record ProcessInfo(
        int pid,
        String name,
        double memoryUsage,
        int threadCount,
        String path
) { }
