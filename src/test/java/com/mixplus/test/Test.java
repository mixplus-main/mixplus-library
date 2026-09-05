package com.mixplus.test;


import com.mixplus.library.Crypto;

import java.util.Base64;

public class Test {
    public static void main(String[] args) {
        String key = Base64.getEncoder().encodeToString(
                Crypto.generateKey().getEncoded()
        );

        System.out.println(key);
    }
}
