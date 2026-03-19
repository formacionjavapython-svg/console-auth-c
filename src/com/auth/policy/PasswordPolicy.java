package com.auth.policy;

import java.util.ArrayList;
import java.util.List;

public class PasswordPolicy {

    private final List<PasswordRule> rules = new ArrayList<>();

    public void addRule(PasswordRule rule) {
        rules.add(rule);
    }

    public List<String> validate(String password) {
        List<String> errors = new ArrayList<>();

        for (PasswordRule rule : rules) {
            if (!rule.validate(password)) {
                errors.add(rule.getMessage());
            }
        }

        return errors;
    }
}