package com.auth.app.validation;

import java.util.ArrayList;
import java.util.List;

import com.auth.app.model.Email;

public class ContainsNumberRule implements PasswordRule {
        @Override
    public List<String> validate(String password, Email email) {
        List<String> errors = new ArrayList<>();

        if (password == null || !password.matches(".*\\d.*")) {
            errors.add("Debe incluir un número.");
        }

        return errors;
    }
    
}
