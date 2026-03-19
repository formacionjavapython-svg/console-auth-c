package security;

import model.Email;
import java.util.*;

public class PasswordPolicy {

    public List<String> validate(String password, Email email) {
        List<String> errors = new ArrayList<>();

        if (password.length() < 8)
            errors.add("Min 8 characters");

        if (!password.matches(".*\\d.*"))
            errors.add("Must contain a number");

        if (password.contains(email.getValue()))
            errors.add("Password cannot contain email");

        return errors;
    }
}