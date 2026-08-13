package com.mixplus.library.network;

import java.io.IOException;
import java.net.InetAddress;

public final class Ping {
    private Ping() {

    }

    public static boolean ping(String host) throws IOException {
        return InetAddress.getByName(host).isReachable(1000);
    }
}
