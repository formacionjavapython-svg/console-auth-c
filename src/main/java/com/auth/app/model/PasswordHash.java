package com.auth.app.model;

import com.auth.app.security.HashUtil;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Objects;

public final class PasswordHash {
    private final byte[] hash;
    private final byte[] salt;

    public PasswordHash(byte[] hash, byte[] salt) {
        this.hash = Objects.requireNonNull(hash);
        this.salt = Objects.requireNonNull(salt);
    }

    public static PasswordHash create(String password, String pepper) {
        byte[] salt = HashUtil.generateSalt();
        byte[] hash = HashUtil.computeHash(password, pepper, salt);
        return new PasswordHash(hash, salt);
    }

    public boolean matches(String password, String pepper) {
        byte[] attempt = HashUtil.computeHash(password, pepper, salt);

        return MessageDigest.isEqual(hash, attempt);
    }

    public byte[] getSalt() {
        return salt.clone();
    }

    public byte[] getHash() {
        return hash.clone();
    }

    @Override
    public String toString() {
        return "PasswordHash[PROTECTED]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PasswordHash)) return false;
        PasswordHash that = (PasswordHash) o;
        return Arrays.equals(hash, that.hash) &&
               Arrays.equals(salt, that.salt);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(hash);
        result = 31 * result + Arrays.hashCode(salt);
        return result;
    }
}
