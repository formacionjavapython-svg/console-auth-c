
package sistema.Rules;


import java.util.ArrayList;
import java.util.List;
public class PasswordPolicy {
    private List<PasswordRules> rules = new ArrayList<>();

    public void addRule(PasswordRules rule) {
        rules.add(rule);
    }

    public List<String> validate(String password, String email) {
        List<String> errores = new ArrayList<>();

        for (PasswordRules rule : rules) {
            String error = rule.validate(password, email);
            if (error != null) {
                errores.add(error);
            }
        }

        return errores;
    }
}
