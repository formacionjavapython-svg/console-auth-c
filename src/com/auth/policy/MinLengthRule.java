package com.auth.policy;

public class MinLengthRule implements PasswordRule {

    private final int minLength;

    public MinLengthRule(int minLength) {
        this.minLength = minLength;
    }

    @Override
    public boolean validate(String password) {
        return password != null && password.length() >= minLength;
    }

    @Override
    public String getMessage() {
        return "Password must have at least " + minLength + " characters";
    }
}