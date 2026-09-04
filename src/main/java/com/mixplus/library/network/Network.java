package com.mixplus.library.network;

import oshi.SystemInfo;
import oshi.hardware.NetworkIF;

import java.util.List;

public final class Network {

    private static final List<NetworkIF> NETWORK_INTERFACES =
            new SystemInfo().getHardware().getNetworkIFs();

    private Network() {
    }

    /**
     * Returns the names of all network interfaces.
     *
     * @return a list of network interface names
     */
    public static List<String> getInterfaces() {
        return NETWORK_INTERFACES.stream()
                .map(NetworkIF::getName)
                .toList();
    }

    /**
     * Returns the IPv4 addresses of the specified network interface.
     *
     * @param name the network interface name
     * @return the IPv4 addresses
     */
    public static String[] getIpAddresses(String name) {
        return getNetworkInterface(name).getIPv4addr();
    }

    /**
     * Returns the MAC address of the specified network interface.
     *
     * @param name the network interface name
     * @return the MAC address
     */
    public static String getMacAddress(String name) {
        return getNetworkInterface(name).getMacaddr();
    }

    /**
     * Returns the total number of bytes received by the specified network interface.
     *
     * @param name the network interface name
     * @return the number of received bytes
     */
    public static long getBytesReceived(String name) {
        return getNetworkInterface(name).getBytesRecv();
    }

    /**
     * Returns the total number of bytes sent by the specified network interface.
     *
     * @param name the network interface name
     * @return the number of sent bytes
     */
    public static long getBytesSent(String name) {
        return getNetworkInterface(name).getBytesSent();
    }

    private static NetworkIF getNetworkInterface(String name) {
        return NETWORK_INTERFACES.stream()
                .filter(network -> network.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Network interface not found: " + name
                        )
                );
    }
}