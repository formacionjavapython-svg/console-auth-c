import java.util.ArrayList;
import java.util.List;

public class NoEmailInPasswordRule implements PasswordRule {
    @Override
    public List<String > validate (String password, Email email){
        List<String> violations = new ArrayList<>();
        if (password != null && email != null && password.contains(email.getValue())){
            violations.add("la contraseña no puede contener tu direccion email");
        }
        return violations;
    }
}
