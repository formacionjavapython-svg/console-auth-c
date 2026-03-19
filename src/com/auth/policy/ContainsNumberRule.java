package com.auth.policy;

public class ContainsNumberRule implements PasswordRule {

    @Override
    public boolean validate(String password) {
        if (password == null) return false;

        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getMessage() {
        return "Password must contain at least one number";
    }
}