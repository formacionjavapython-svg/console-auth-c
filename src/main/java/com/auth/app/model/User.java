package com.auth.app.model;

import java.util.Objects;

public final class User {

    private final Email email; 
    private final PasswordHash hash;

    public User(Email email, PasswordHash hash) {
        this.email = Objects.requireNonNull(email);
        this.hash = Objects.requireNonNull(hash);
    }

    public Email getEmail() {
        return email;
    }

    public PasswordHash getHash() {
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }
}