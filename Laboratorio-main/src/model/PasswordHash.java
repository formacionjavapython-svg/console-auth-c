package model;

import java.security.MessageDigest;
import security.PasswordUtils;

public class PasswordHash {

    private final byte[] hash;
    private final byte[] salt;

    public PasswordHash(byte[] hash, byte[] salt) {
        this.hash = hash;
        this.salt = salt;
    }

    public boolean matches(String password) {
        byte[] newHash = PasswordUtils.computeHash(password, salt);
        return MessageDigest.isEqual(hash, newHash);
    }

    public byte[] getHash() { return hash; }
    public byte[] getSalt() { return salt; }
}