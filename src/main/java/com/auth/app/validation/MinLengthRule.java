package com.auth.app.validation;

import com.auth.app.model.Email;
import java.util.ArrayList;
import java.util.List;

public class MinLengthRule implements PasswordRule {
        @Override
    public List<String> validate(String password, Email email) {
        List<String> errors = new ArrayList<>();

        if (password == null || password.length() < 8) {
            errors.add("Mínimo 8 caracteres.");
        }

        return errors;
    }
    
}
