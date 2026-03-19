import java.util.ArrayList;
import java.util.List;

public class PasswordPolicy {

    private List<PasswordRule> rules = new ArrayList<>();

    public void addRule(PasswordRule rule) {
        rules.add(rule);
    }

    public List<String> validate(String password) {
        List<String> errors = new ArrayList<>();

        for (PasswordRule rule : rules) {
            String result = rule.validate(password);

            if (result != null) {
                errors.add(result);
            }
        }

        return errors;
    }
}