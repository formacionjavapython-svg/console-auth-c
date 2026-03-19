import java.util.ArrayList;
import java.util.List;

public class PasswordPolicy {
    private final List<PasswordRule> rules = new ArrayList<>();

    public PasswordPolicy() {
        rules.add(new MinLengthRule());
        rules.add(new ContainsNumberRule());
        rules.add(new NoEmailInPasswordRule());
    }

    public List<String> validate(String password, Email email) {
        List<String> violations = new ArrayList<>();
        for (PasswordRule rule : rules) {
            String error = rule.validate(password, email);
            if (error != null) violations.add(error);
        }
        return violations;
    }
}