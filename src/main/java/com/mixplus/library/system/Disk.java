package com.mixplus.library.system;

import com.mixplus.library.unit.DataUnit;
import com.mixplus.library.unit.Percentage;
import oshi.SystemInfo;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;

import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * Provides utility methods for retrieving disk storage information.
 *
 * <p>This class uses OSHI to obtain information about the file system and
 * disk storage. The specified path must correspond to a mounted file system
 * or disk recognized by the operating system.</p>
 *
 * <p>This class cannot be instantiated.</p>
 */
public final class Disk {
    private static final FileSystem FILE_SYSTEM =
            new SystemInfo().getOperatingSystem().getFileSystem();

    private Disk() {

    }

    /**
     * Returns the total storage capacity of the disk containing the specified path.
     * @param path the path used to identify the disk
     * @return the total storage capacity in bytes
     * @throws IllegalArgumentException if no disk corresponding to the path is found
     */
    public static long getTotal(Path path) {
        return getFileStore(path).getTotalSpace();
    }

    /**
     * Returns the total storage capacity of the disk containing the specified path
     * in the specified data unit.
     *
     *
     * @param path the path used to identify the disk
     * @param unit the data unit used for the result
     * @return the total storage capacity in the specified unit
     * @throws IllegalArgumentException if no disk corresponding to the path is found
     */
    public static long getTotal(Path path, DataUnit unit) {
        return unit.fromBytes(getTotal(path));
    }

    /**
     * Returns the available storage space of the disk containing the specified path.
     *
     * <p>The available space is the amount of storage currently available to the
     * application.</p>
     *
     * @param path the path used to identify the disk
     * @return the available storage space in bytes
     * @throws IllegalArgumentException if no disk corresponding to the path is found
     */
    public static long getAvailable(Path path) {
        return getFileStore(path).getUsableSpace();
    }

    /**
     * Returns the available storage space of the disk containing the specified path
     * in the specified data unit.
     *
     * @param path the path used to identify the disk
     * @param unit the data unit used for the result
     * @return the available storage space in the specified unit
     * @throws IllegalArgumentException if no disk corresponding to the path is found
     */
    public static long getAvailable(Path path, DataUnit unit) {
        return unit.fromBytes(getAvailable(path));
    }

    /**
     * Returns the amount of used storage space of the disk containing the specified path.
     *
     * <p>The used space is calculated as:
     * {@code total space - available space}.</p>
     *
     * @param path the path used to identify the disk
     * @return the used storage space in bytes
     * @throws IllegalArgumentException if no disk corresponding to the path is found
     */
    public static long getUsed(Path path) {
        return getTotal(path) - getAvailable(path);
    }

    /**
     * Returns the amount of used storage space of the disk containing the specified path
     * in the specified data unit.
     *
     * @param path the path used to identify the disk
     * @param unit the data unit used for the result
     * @return the used storage space in the specified unit
     * @throws IllegalArgumentException if no disk corresponding to the path is found
     */
    public static long getUsed(Path path, DataUnit unit) {
        return unit.fromBytes(getUsed(path));
    }

    /**
     * Returns the storage usage of the disk containing the specified path.
     *
     * <p>The returned value represents the percentage of used storage.
     * For example, a return value of {@code 75.0} means that 75% of the
     * disk capacity is currently used.</p>
     *
     * @param path the path used to identify the disk
     * @return the disk usage percentage
     * @throws IllegalArgumentException if no disk corresponding to the path is found
     */
    public static double getUsage(Path path) {
        return Percentage.of(getUsed(path), getTotal(path));
    }

    /**
     * Finds the file store corresponding to the specified path.
     *
     * <p>The path and each file store mount point are converted to absolute,
     * normalized paths before comparison.</p>
     *
     * @param path the path used to identify the disk
     * @return the file store corresponding to the path
     * @throws IllegalArgumentException if no corresponding file store is found
     */
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
