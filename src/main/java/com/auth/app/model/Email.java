package com.auth.app.model;

import java.util.Objects;

public final class Email {

    private final String value;

    public Email(String value) {
        if (value == null || !value.contains("@") || !value.contains(".")) {
            throw new IllegalArgumentException("Invalid email");
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Email)) return false;
        Email email = (Email) o;
        return value.equals(email.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}