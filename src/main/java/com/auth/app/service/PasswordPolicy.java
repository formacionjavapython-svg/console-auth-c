package com.auth.app.service;

import com.auth.app.model.Email;
import com.auth.app.validation.PasswordRule;
import java.util.ArrayList;
import java.util.List;

public class PasswordPolicy {
    private final List<PasswordRule> rules;

    public PasswordPolicy(List<PasswordRule> rules) {
        this.rules = rules;
    }

    public List<String> validate(String password, Email email) {

        List<String> violations = new ArrayList<>();

        for (PasswordRule rule : rules) {
            violations.addAll(rule.validate(password, email));
        }

        return violations;
    }

    public boolean isValid(String password, Email email) {
        return validate(password, email).isEmpty();
    }
}
