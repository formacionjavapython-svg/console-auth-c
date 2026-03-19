package auth.security;

import java.util.ArrayList;
import java.util.List;

public class MinLengthRule implements PasswordRule {

    @Override
    public List<String> validate(String password, String email) {
        List<String> errors = new ArrayList<>();

        if (password.length() < 8) {
            errors.add("Password debe tener al menos 8 caracteres");
        }

        return errors;
    }
}