package com.mixplus.library.minecraft.rcon;

public record RconPacket(
        int id,
        int type,
        String body
) {
}
