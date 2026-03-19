
package sistema.Rules;


public class ContainsNumberRule implements PasswordRules {
     @Override
    public String validate(String password, String email) {
        if (!password.matches(".*\\d.*")) {
            return "Debe contener al menos un número";
        }
        return null;
    }
}
