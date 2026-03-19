import java.util.Collections;
import java.util.List;

/**
 * Regla: la contrasena debe contener al menos una letra mayuscula.
 */
public final class ContainsUppercaseRule implements PasswordRule {

    @Override
    public List<String> validate(final String password, final String email) {
        if (password == null || !password.chars().anyMatch(Character::isUpperCase)) {
            return List.of("Debe contener al menos una letra mayuscula.");
        }
        return Collections.emptyList();
    }
}
