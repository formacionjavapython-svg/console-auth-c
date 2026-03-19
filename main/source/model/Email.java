package source.model;

import java.util.Objects;

public class Email {private String email;

    public Email(String email) {
        if (email == null || email.isBlank())
            throw new IllegalArgumentException("Email no puede estar vacío");
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Email)) return false;
        Email email1 = (Email) o;
        return email.equalsIgnoreCase(email1.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email.toLowerCase());
    }
}
