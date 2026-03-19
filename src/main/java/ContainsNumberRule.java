import java.util.Collections;
import java.util.List;

/**
 * Regla: la contrasena debe contener al menos un digito.
 * Busca al menos un digito usando iteracion de chars.
 * Promueve passwords mas complejos.
 */
public final class ContainsNumberRule implements PasswordRule {

    @Override
    public List<String> validate(final String password, final String email) {
        if (password == null || !password.chars().anyMatch(Character::isDigit)) {
            return List.of("Debe contener al menos un numero.");
        }
        return Collections.emptyList();
    }
}
