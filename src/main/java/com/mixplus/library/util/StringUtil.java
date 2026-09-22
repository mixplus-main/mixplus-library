package com.mixplus.library.util;

import java.util.UUID;

public class StringUtil {
    public static boolean isValidIdentifier(String value) {
        return value != null && value.matches("[a-zA-Z0-9_]+");
    }

    public static UUID toUUID(String value) {
        return UUID.fromString(
                value.replaceFirst(
                        "(........)(....)(....)(....)(............)",
                        "$1-$2-$3-$4-$5"
                )
        );
    }
}
