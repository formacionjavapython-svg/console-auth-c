package com.consoleauthlab.model;

/**
 * Clase que representa un usuario en el sistema.
 */
public class User {
    private final Email email;
    private String passwordHash;

    public User(Email email, String passwordHash) {
        if (email == null) {
            throw new IllegalArgumentException("Email no puede ser nulo");
        }
        if (passwordHash == null || passwordHash.trim().isEmpty()) {
            throw new IllegalArgumentException("PasswordHash no puede ser nulo o vacío");
        }
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public Email getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }

    @Override
    public String toString() {
        return "User{" +
                "email=" + email +
                ", passwordHash='" + passwordHash.substring(0, 10) + "...'" +
                '}';
    }
}