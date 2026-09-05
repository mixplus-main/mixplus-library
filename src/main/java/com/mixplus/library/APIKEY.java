package com.mixplus.library;

import java.security.SecureRandom;
import java.util.List;

public class APIKEY {
    private static final String CHARACTERS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private static final SecureRandom random = new SecureRandom();

    public static String generate(String prefix, int length) {
        StringBuilder key = new StringBuilder(length);

        key.append(prefix);
        for (int i = 0; i < length; i++) {
            key.append(
                    CHARACTERS.charAt(
                            random.nextInt(CHARACTERS.length()
                            )
                    )
            );
        }
        return key.toString();

    }

    public static boolean isValid(String apiKey, int length, String prefix) {
        if (apiKey == null || prefix == null) {
            return false;
        }

        if (!apiKey.startsWith(prefix)) {
            return false;
        }

        String key = apiKey.substring(prefix.length());

        if (key.length() != length) {
            return false;
        }

        return key.chars()
                .allMatch(c -> CHARACTERS.indexOf(c) >= 0);
    }

    public static boolean isRegistered(
            List<String> registeredKeys,
            String apiKey,
            int length,
            String prefix
    ) {
        if (!isValid(apiKey, length, prefix)) {
            return false;
        }

        return registeredKeys != null && registeredKeys.contains(apiKey);
    }
}
