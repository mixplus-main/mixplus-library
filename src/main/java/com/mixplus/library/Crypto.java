package com.mixplus.library;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

public class Crypto {
    private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final int IV_LENGTH = 12;
    private static final int TAG_LENGTH = 128;
    private static final SecureRandom random = new SecureRandom();

    public static SecretKey generateKey() {
        try {
            KeyGenerator generator = KeyGenerator.getInstance("AES");
            generator.init(256);
            return generator.generateKey();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String encrypt(String text, SecretKey key) {
        try {
            byte[] iv = new byte[IV_LENGTH];
            random.nextBytes(iv);

            Cipher cipher = Cipher.getInstance(ALGORITHM);

            cipher.init(
                    Cipher.ENCRYPT_MODE,
                    key,
                    new GCMParameterSpec(TAG_LENGTH, iv)
            );

            byte[] encrypted = cipher.doFinal(
                    text.getBytes(StandardCharsets.UTF_8)
            );

            byte[] result = new byte[iv.length + encrypted.length];

            System.arraycopy(iv, 0, result, 0, iv.length);
            System.arraycopy(
                    encrypted,
                    0,
                    result,
                    iv.length,
                    encrypted.length
            );

            return Base64.getEncoder().encodeToString(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String decrypt(String encryptedText, SecretKey key) {
        try {
            byte[] data = Base64.getDecoder().decode(encryptedText);

            byte[] iv = new byte[IV_LENGTH];
            byte[] encrypted = new byte[data.length - IV_LENGTH];

            System.arraycopy(data, 0, iv, 0, IV_LENGTH);
            System.arraycopy(
                    data,
                    IV_LENGTH,
                    encrypted,
                    0,
                    encrypted.length
            );

            Cipher cipher = Cipher.getInstance(ALGORITHM);

            cipher.init(
                    Cipher.DECRYPT_MODE,
                    key,
                    new GCMParameterSpec(TAG_LENGTH, iv)
            );

            byte[] decrypted = cipher.doFinal(encrypted);

            return new String(
                    decrypted,
                    StandardCharsets.UTF_8
            );

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
