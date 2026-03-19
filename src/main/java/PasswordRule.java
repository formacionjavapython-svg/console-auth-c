import java.util.List;

/**
 * Interfaz funcional para reglas de contrasena composables.
 * Contrato simple: validate(password, email) retorna lista de violaciones.
 * Cada regla es independiente y puede combinarse con otras,
 * permitiendo extensibilidad sin modificar codigo existente (Open/Closed).
 */
public interface PasswordRule {

    /**
     * Valida la contrasena contra esta regla.
     *
     * @param password contrasena en texto plano
     * @param email    email del usuario (para reglas como NoEmailInPasswordRule)
     * @return lista de violaciones encontradas (vacia si cumple la regla)
     */
    List<String> validate(String password, String email);
}
