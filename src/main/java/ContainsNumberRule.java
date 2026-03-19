import java.util.ArrayList;
import java.util.List;

public class ContainsNumberRule implements PasswordRule{
    @Override
    public List<String> validate (String password, Email email){
        List<String> violations = new ArrayList<>();

        if (password == null || !password.matches(".*\\d.*")) {
            violations.add("La contraseña debe contener al menos un número.");
        }
        return violations;

    }

}
