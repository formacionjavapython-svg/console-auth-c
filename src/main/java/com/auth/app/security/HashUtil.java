package com.auth.app.security;

import java.security.SecureRandom;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class HashUtil {
    private static final int ITERATIONS = 100_000;
    private static final int KEY_LENGTH = 256;

    public static byte[] generateSalt() {
        byte[] salt = new byte[16];
        try {
            SecureRandom.getInstanceStrong().nextBytes(salt);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return salt;
    }

    public static byte[] computeHash(String password, String pepper, byte[] salt) {
        try {
            String combined = password + pepper;

            PBEKeySpec spec = new PBEKeySpec(
                combined.toCharArray(),
                salt,
                ITERATIONS,
                KEY_LENGTH
            );

            SecretKeyFactory factory =
                SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

            return factory.generateSecret(spec).getEncoded();

        } catch (Exception e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }   
}
