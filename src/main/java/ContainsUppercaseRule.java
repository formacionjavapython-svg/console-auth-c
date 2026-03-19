// Regla: la contraseña debe tener al menos UNA letra mayúscula
// Nada fancy, solo revisa con regex.
// Si cumple → devuelve null (todo bien)
// Si no cumple → devuelve mensaje de error

public class ContainsUppercaseRule implements PasswordRule {

    @Override
    public String validate(String password, String email) {

        // .* = lo que sea antes o después
        // [A-Z] = cualquier letra mayúscula
        boolean tieneMayuscula = password.matches(".*[A-Z].*");

        if (tieneMayuscula) {
            return null; // OK, pasa la regla
        } else {
            return "Debe incluir al menos una letra mayúscula.";
        }
    }
}