package auth.security;

import java.util.ArrayList;
import java.util.List;

public class NoEmailInPasswordRule implements PasswordRule {

    @Override
    public List<String> validate(String password, String email) {
        List<String> errors = new ArrayList<>();

        if (!email.contains("@")) {
            return errors; // evita crash si email es inválido
        }

        String[] parts = email.split("@");

        String userPart = parts[0];
        String domainPart = parts[1];

        if (password.contains(userPart) || password.contains(domainPart)) {
            errors.add("Password no debe contener partes del email");
        }

        return errors;
    }
}