package auth.security;

import java.util.ArrayList;
import java.util.List;

public class ContainsUppercaseRule implements PasswordRule {

    @Override
    public List<String> validate(String password, String email) {
        List<String> errors = new ArrayList<>();

        boolean hasUppercase = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUppercase = true;
                break;
            }
        }

        if (!hasUppercase) {
            errors.add("Password debe contener al menos una letra mayúscula");
        }

        return errors;
    }
}