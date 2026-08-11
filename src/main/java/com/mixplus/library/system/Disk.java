package com.mixplus.library.system;

import com.mixplus.library.unit.DataUnit;
import com.mixplus.library.unit.Percentage;
import oshi.SystemInfo;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class Disk {
    private static final FileSystem FILE_SYSTEM =
            new SystemInfo().getOperatingSystem().getFileSystem();

    private Disk() {

    }

    public static long getTotal(Path path) {
        return getFileStore(path).getTotalSpace();
    }

    public static long getTotal(Path path, DataUnit unit) {
        return unit.fromBytes(getTotal(path));
    }

    public static long getAvailable(Path path) {
        return getFileStore(path).getUsableSpace();
    }

    public static long getAvailable(Path path, DataUnit unit) {
        return unit.fromBytes(getAvailable(path));
    }

    public static long getUsed(Path path) {
        return getTotal(path) - getAvailable(path);
    }

    public static long getUsed(Path path, DataUnit unit) {
        return unit.fromBytes(getUsed(path));
    }

    public static double getUsage(Path path) {
        return Percentage.of(getUsed(path), getTotal(path));
    }



    private static OSFileStore getFileStore(Path path) {
        String target = path.toAbsolutePath()
                .normalize()
                .toString();


        for (OSFileStore store : FILE_SYSTEM.getFileStores()) {
            String mount = Paths.get(store.getMount())
                    .toAbsolutePath()
                    .normalize()
                    .toString();

            if (mount.equalsIgnoreCase(target)) {
                return store;
            }
        }
        throw new IllegalArgumentException("Disk not found: " + path);
    }

}
