package com.auth.domain;

import java.util.Objects;

public class User {

    private final Email email;
    private final PasswordHash passwordHash;

    public User(Email email, PasswordHash passwordHash) {
        if (email == null || passwordHash == null) {
            throw new IllegalArgumentException("Email and passwordHash cannot be null");
        }
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public Email getEmail() {
        return email;
    }

    public PasswordHash getPasswordHash() {
        return passwordHash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}