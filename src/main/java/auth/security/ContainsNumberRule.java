package auth.security;

import java.util.ArrayList;
import java.util.List;

public class ContainsNumberRule implements PasswordRule {

    @Override
    public List<String> validate(String password, String email) {
        List<String> errors = new ArrayList<>();

        boolean hasNumber = false;

        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                hasNumber = true;
                break;
            }
        }

        if (!hasNumber) {
            errors.add("Password debe contener al menos un número");
        }

        return errors;
    }
}