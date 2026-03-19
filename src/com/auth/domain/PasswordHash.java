package com.auth.domain;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordHash {

    private final String hash;
    private final String salt;

    public PasswordHash(String password) {
        this.salt = generateSalt();
        this.hash = hashPassword(password, salt);
    }

    private String generateSalt() {
        byte[] saltBytes = new byte[16];
        new SecureRandom().nextBytes(saltBytes);
        return Base64.getEncoder().encodeToString(saltBytes);
    }

    private String hashPassword(String password, String salt) {
        try {
            String pepper = "SECRET";

            PBEKeySpec spec = new PBEKeySpec(
                    (password + pepper).toCharArray(),
                    Base64.getDecoder().decode(salt),
                    100000,
                    256
            );

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

            byte[] hashBytes = factory.generateSecret(spec).getEncoded();

            return Base64.getEncoder().encodeToString(hashBytes);

        } catch (Exception e) {
            throw new RuntimeException("Error hashing password");
        }
    }

    public boolean matches(String password) {
        String newHash = hashPassword(password, salt);
        return MessageDigest.isEqual(
                hash.getBytes(),
                newHash.getBytes()
        );
    }

    public String getHash() {
        return hash;
    }

    public String getSalt() {
        return salt;
    }
}