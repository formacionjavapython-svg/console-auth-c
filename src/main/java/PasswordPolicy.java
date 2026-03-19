import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Clase que mantiene una lista de reglas y ejecuta validate() agregando
 * todas las violaciones. Si la lista esta vacia, el password es valido.
 *
 * Diseno funcional composable: cada regla es independiente y puede
 * combinarse con otras sin modificar codigo existente.
 */
public final class PasswordPolicy {

    private final List<PasswordRule> rules;

    public PasswordPolicy(final List<PasswordRule> rules) {
        this.rules = Collections.unmodifiableList(new ArrayList<>(rules));
    }

    /**
     * Ejecuta todas las reglas y retorna la lista agregada de violaciones.
     *
     * @param password contrasena en texto plano
     * @param email    email del usuario
     * @return lista de violaciones (vacia = password valido)
     */
    public List<String> validate(final String password, final String email) {
        final List<String> violations = new ArrayList<>();
        for (final PasswordRule rule : rules) {
            violations.addAll(rule.validate(password, email));
        }
        return Collections.unmodifiableList(violations);
    }

    /**
     * Crea la politica por defecto del laboratorio:
     * - Minimo 8 caracteres
     * - Al menos una mayuscula
     * - Al menos un numero
     * - No incluir email en la contrasena
     */
    public static PasswordPolicy defaultPolicy() {
        return new PasswordPolicy(List.of(
                new MinLengthRule(8),
                new ContainsUppercaseRule(),
                new ContainsNumberRule(),
                new NoEmailInPasswordRule()
        ));
    }
}
