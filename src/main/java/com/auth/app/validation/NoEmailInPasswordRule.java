package com.auth.app.validation;

import java.util.ArrayList;
import java.util.List;

import com.auth.app.model.Email;

public class NoEmailInPasswordRule implements PasswordRule {
    @Override
    public List<String> validate(String password, Email email) {
        List<String> errors = new ArrayList<>();

        if (password == null || email == null) return errors;

        String emailValue = email.getValue().toLowerCase();
        String pass = password.toLowerCase();

        String[] parts = emailValue.split("@");

        for (String part : parts) {
            part = part.replace(".", "");
            if (part.length() > 2 && pass.contains(part)) {
                errors.add("La contraseña no debe contener partes del email.");
                break;
            }
        }

        return errors;
    }
}
