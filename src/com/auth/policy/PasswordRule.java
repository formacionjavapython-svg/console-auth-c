package com.auth.policy;

public interface PasswordRule {

    boolean validate(String password);

    String getMessage();
}