
package sistema.Rules;


public class MinLenghtRule implements PasswordRules{
     @Override
    public String validate(String password, String email) {
        if (password.length() < 8) {
            return "Debe tener al menos " + 8 + " caracteres";
        }
        return null;
}}
