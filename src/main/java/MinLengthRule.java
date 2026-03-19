import java.util.Collections;
import java.util.List;

/**
 * Regla: verifica longitud minima configurable.
 * Ejemplo: minimo 8 caracteres. Retorna mensaje descriptivo si falla.
 */
public final class MinLengthRule implements PasswordRule {

    private final int minLength;

    public MinLengthRule(final int minLength) {
        this.minLength = minLength;
    }

    @Override
    public List<String> validate(final String password, final String email) {
        if (password == null || password.length() < minLength) {
            return List.of("Debe tener al menos " + minLength + " caracteres.");
        }
        return Collections.emptyList();
    }
}
