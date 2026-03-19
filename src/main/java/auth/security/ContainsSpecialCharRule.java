package auth.security;

import java.util.ArrayList;
import java.util.List;

public class ContainsSpecialCharRule implements PasswordRule {

    @Override
    public List<String> validate(String password, String email) {
        List<String> errors = new ArrayList<>();

        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) {
                hasSpecial = true;
                break;
            }
        }

        if (!hasSpecial) {
            errors.add("Password debe contener al menos un símbolo");
        }

        return errors;
    }
}