package com.mixplus.library.system;

public record ProcessInfo(
        int pid,
        String name,
        long memoryUsage,
        int threadCount,
        String path
) { }
