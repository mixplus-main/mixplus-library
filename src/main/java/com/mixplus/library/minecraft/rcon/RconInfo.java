package com.mixplus.library.minecraft.rcon;

public record RconInfo(
        String host,
        int port,
        String password
) {
}
