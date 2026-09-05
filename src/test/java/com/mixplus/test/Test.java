package com.mixplus.test;


import com.mixplus.library.Crypto;
import com.mixplus.library.system.OperatingSystem;
import com.mixplus.library.system.ProcessInfo;
import com.mixplus.library.unit.DataUnit;

import java.util.Base64;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<ProcessInfo> list = OperatingSystem.getProcessList(DataUnit.GB);

        for (ProcessInfo info : list) {
            System.out.println("pid: " + info.pid());
            System.out.println("name: " + info.name());
            System.out.println("memoryUsage: " + info.memoryUsage());
            System.out.println("threadCount: " + info.threadCount());
        }
    }
}
