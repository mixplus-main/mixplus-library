package com.mixplus.library.util;

import java.security.SecureRandom;
import java.util.UUID;

public class StringUtil {
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final String CHARACTERS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";


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

    public static String generateCode(int length) {
        StringBuilder code = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            code.append(CHARACTERS.charAt(
                    RANDOM.nextInt(CHARACTERS.length())
            ));
        }

        return code.toString();
    }
}
