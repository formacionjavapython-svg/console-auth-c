package auth.security;

import java.util.ArrayList;
import java.util.List;

public class PasswordPolicy {

    private List<PasswordRule> rules = new ArrayList<>();

    public PasswordPolicy() {
       rules.add(new ContainsLetterRule());
       rules.add(new ContainsSpecialCharRule());
       rules.add(new ContainsNumberRule());
       rules.add(new NoEmailInPasswordRule());
       rules.add(new ContainsUppercaseRule());
       rules.add(new MinLengthRule());
    }

    public List<String> validate(String password, String email) {
        List<String> errors = new ArrayList<>();

        for (PasswordRule rule : rules) {
            errors.addAll(rule.validate(password, email));
        }

        return errors;
    }
}