package main.java.model;

public class Email {
    private String email;

    public Email(String email) {
        if (!isValid(email)) throw new IllegalArgumentException("Email no válido.");
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    // público
    public boolean isEmailValid() {
        return isValid(this.email);
    }

    private boolean isValid(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }
}
