import java.util.Collections;
import java.util.List;

/**
 * Regla: previene que el usuario incluya su email en la contrasena.
 * Itera sobre partes del email comparando substrings.
 */
public final class NoEmailInPasswordRule implements PasswordRule {

    @Override
    public List<String> validate(final String password, final String email) {
        if (password == null || email == null) {
            return Collections.emptyList();
        }

        final String lowerPassword = password.toLowerCase();
        final String lowerEmail = email.toLowerCase();

        // Verificar el email completo
        if (lowerPassword.contains(lowerEmail)) {
            return List.of("La contrasena no debe contener tu email.");
        }

        // Verificar la parte local del email (antes del @)
        final int atIndex = lowerEmail.indexOf('@');
        if (atIndex > 0) {
            final String localPart = lowerEmail.substring(0, atIndex);
            if (localPart.length() >= 3 && lowerPassword.contains(localPart)) {
                return List.of("La contrasena no debe contener partes de tu email.");
            }
        }

        return Collections.emptyList();
    }
}
