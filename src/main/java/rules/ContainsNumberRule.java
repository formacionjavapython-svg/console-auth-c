package rules;

import java.util.ArrayList;
import java.util.List;

public class ContainsNumberRule implements PasswordRule {
    @Override
    public List<String> validate(String password, Email email) {
        List<String> errors = new ArrayList<>();
        if (!password.matches(".*\\d.*")) {
            errors.add("la contraseña debe contener al menos un número.");
        }
        return errors;
    }
}