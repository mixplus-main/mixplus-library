package com.mixplus.library.unit;

public class StringUtil {
    public static boolean isValidIdentifier(String value) {
        return value != null && value.matches("[a-zA-Z0-9_]+");
    }
}
