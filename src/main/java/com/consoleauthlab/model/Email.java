package com.consoleauthlab.model;

/**
 * Clase para representar y validar direcciones de email.
 */
public class Email {
    private final String value;
    
    public Email(String value) {
        if (!isValid(value)) {
            throw new IllegalArgumentException("Formato de email inválido: " + value);
        }
        this.value = value;
    }
    
    public static boolean isValid(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }
    
    public String getValue() {
        return value;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Email email = (Email) o;
        return value.equals(email.value);
    }
    
    @Override
    public int hashCode() {
        return value.hashCode();
    }
    
    @Override
    public String toString() {
        return value;
    }
}