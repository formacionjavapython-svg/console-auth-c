package src.main.java;

import java.util.Objects;

public interface PasswordRule {
    String validate(String password, String email);
}

class MinLengthRule implements PasswordRule {
    private final int min;

    public MinLengthRule(int min) {
        this.min = min;
    }

    @Override
    public String validate(String password, String email) {
        if (password.length() >= min) {
            return null;
        } else {
            return "Mínimo " + min + " caracteres.";
        }
    }
}

class ContainsNumberRule implements PasswordRule {
    @Override
    public String validate(String password, String email) {
        if (password.matches(".*[0-9].*")) {
            return null;
        } else {
            return "Debe incluir un número.";
        }
    }
}

class NoEmailInPasswordRule implements PasswordRule {
    @Override
    public String validate(String password, String email) {
        Objects.requireNonNull(email);
        String handle = email.split("@")[0].replace(".", "").toLowerCase();
        
        if (password.toLowerCase().contains(handle)) {
            return "La contraseña no puede contener tu email.";
        }
        return null;
    }
}
