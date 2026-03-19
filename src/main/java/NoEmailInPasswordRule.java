import java.util.ArrayList;
import java.util.List;

public class NoEmailInPasswordRule implements PasswordRule {
    @Override
    public List<String> validate(String password, Email email) {
        List<String> violations = new ArrayList<>();
        if (password.contains(email.getValue())) {
            violations.add("La contraseña no puede contener el email.");
        }
        return violations;
    }
}