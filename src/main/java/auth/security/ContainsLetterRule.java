package auth.security;

import java.util.ArrayList;
import java.util.List;

public class ContainsLetterRule implements PasswordRule {

    @Override
    public List<String> validate(String password, String email) {
        List<String> errors = new ArrayList<>();

        boolean hasLetter = false;

        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) {
                hasLetter = true;
                break;
            }
        }

        if (!hasLetter) {
            errors.add("Password debe contener al menos una letra");
        }

        return errors;
    }
}