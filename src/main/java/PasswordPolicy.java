import java.util.ArrayList;
import java.util.List;

public class PasswordPolicy {
    private final List<PasswordRule> rules;

    public PasswordPolicy(List<PasswordRule> rules) {
        this.rules = rules;
    }

    public List<String> validate(String password, Email email) {
        List<String> allViolations = new ArrayList<>();
        for (PasswordRule rule : rules) {
            allViolations.addAll(rule.validate(password, email));
        }
        return allViolations;
    }
}
