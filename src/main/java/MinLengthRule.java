import java.util.ArrayList;
import java.util.List;

public class MinLengthRule implements PasswordRule {
    private final int minLength;

    public MinLengthRule(int minLength) {
        this.minLength = minLength;
    }

    @Override
    public List<String> validate(String password, Email email) {
        List<String> violations = new ArrayList<>();
        if (password.length() < minLength) {
            violations.add("La contraseña debe tener al menos " + minLength + " caracteres.");
        }
        return violations;
    }
}