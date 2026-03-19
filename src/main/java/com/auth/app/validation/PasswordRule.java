package com.auth.app.validation;

import com.auth.app.model.Email;
import java.util.List;

public interface PasswordRule {
    List<String> validate(String password, Email email);
}