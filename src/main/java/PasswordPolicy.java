import java.util.ArrayList;
import java.util.List;

public class PasswordPolicy {
    private final List<PasswordRule> rules = new ArrayList<>();

    // Agrega una regla a la política
    public void addRule(PasswordRule rule) {
        rules.add(rule);
    }

    // Valida la contraseña contra todas las reglas
    public List<String> validate(String password, Email email) {
        List<String> violations = new ArrayList<>();
        for (PasswordRule rule : rules) {
            violations.addAll(rule.validate(password, email));
        }
        return violations;
    }
}