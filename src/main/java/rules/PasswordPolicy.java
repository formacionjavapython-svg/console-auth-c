package rules;

import java.util.ArrayList;
import java.util.List;

public class PasswordPolicy {
    private final List<PasswordRule> rules = new ArrayList<>();

    public void addRule(PasswordRule rule) {
        rules.add(rule);
    }

    public List<String> validate(String password, Email email) {
        List<String> allErrors = new ArrayList<>();
        
        for (PasswordRule rule : rules) {
            allErrors.addAll(rule.validate(password, email));
        }
        
        return allErrors;
    }
}