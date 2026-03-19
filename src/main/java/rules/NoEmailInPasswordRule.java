package rules;

import java.util.ArrayList;
import java.util.List;

public class NoEmailInPasswordRule implements PasswordRule {
    @Override
    public List<String> validate(String password, Email email) {
        List<String> errors = new ArrayList<>();
        // Extrae el nombre de usuario antes del @
        String emailPart = email.getValue().split("@")[0];
        if (password.toLowerCase().contains(emailPart.toLowerCase())) {
            errors.add("la contraseña no puede contener tu nombre de usuario del email.");
        }
        return errors;
    }
}
