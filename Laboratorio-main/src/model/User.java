package model;

public class User {
    private final Email email;
    private final PasswordHash hash;

    public User(Email email, PasswordHash hash) {
        this.email = email;
        this.hash = hash;
    }

    public Email getEmail() {
        return email;
    }

    public PasswordHash getHash() {
        return hash;
    }
}