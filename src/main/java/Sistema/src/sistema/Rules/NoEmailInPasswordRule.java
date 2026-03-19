
package sistema.Rules;


public class NoEmailInPasswordRule implements PasswordRules{
    @Override
    public String validate(String password, String email) {

        if (email == null) return null;

        String passwordLower = password.toLowerCase();
        String emailLower = email.toLowerCase();

        String[] partes = emailLower.split("@");

        if (partes.length > 0) {
            String nombre = partes[0];

            
            String[] subPartes = nombre.split("[._-]");

            for (String parte : subPartes) {
                if (parte.length() > 2 && passwordLower.contains(parte)) {
                    return "El password no debe contener partes del email";
                }
            }
        }

        return null;
    }
}
