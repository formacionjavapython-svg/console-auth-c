package rules; 
import java.util.ArrayList;
import java.util.List; 

public class MinLengthRule implements PasswordRule {
    private final int min;
    public MinLengthRule(int min) { this.min = min; }

    @Override
    public List<String> validate(String password, Email email) {
        List<String> errors = new ArrayList<>();
        if (password.length() < min) {
            errors.add("la contraseña debe tener al menos " + min + " caracteres.");
        }
        return errors;
    }
}