import java.util.List;
import java.util.Optional;

/**
 * Servicio de autenticacion (logica de negocio).
 * Coordina registro y login aplicando politica de contrasenas,
 * validacion de email y hashing seguro.
 *
 * Mensajes de error genericos para no filtrar informacion
 * (no se revela si un email existe o no).
 */
public final class AuthService {

    private static final String GENERIC_ERROR = "Credenciales invalidas.";

    private final UserRepository userRepository;
    private final HashingService hashingService;
    private final PasswordPolicy passwordPolicy;

    public AuthService(final UserRepository userRepository,
                       final HashingService hashingService,
                       final PasswordPolicy passwordPolicy) {
        this.userRepository = userRepository;
        this.hashingService = hashingService;
        this.passwordPolicy = passwordPolicy;
    }

    /**
     * Registra un nuevo usuario.
     *
     * @param email    email del usuario
     * @param password contrasena en texto plano
     * @return resultado de la operacion
     */
    public AuthResult register(final String email, final String password) {
        if (!EmailValidator.isValid(email)) {
            return AuthResult.fail("Formato de email invalido.");
        }

        final List<String> violations = passwordPolicy.validate(password, email);
        if (!violations.isEmpty()) {
            return AuthResult.fail(
                    "La contrasena no cumple la politica:\n  - "
                    + String.join("\n  - ", violations)
            );
        }

        if (userRepository.existsByEmail(email)) {
            // Mensaje generico: no revelamos que el email ya existe
            return AuthResult.fail(GENERIC_ERROR);
        }

        final String salt = hashingService.generateSalt();
        final String hash = hashingService.hash(password, salt);
        final User user = new User(email, hash, salt);

        if (userRepository.save(user)) {
            return AuthResult.ok("Registro exitoso. Bienvenido/a.");
        }

        return AuthResult.fail(GENERIC_ERROR);
    }

    /**
     * Inicia sesion verificando credenciales.
     * Retorna exito o fallo generico (sin filtrar informacion).
     *
     * @param email    email del usuario
     * @param password contrasena en texto plano
     * @return resultado de la operacion
     */
    public AuthResult login(final String email, final String password) {
        if (email == null || email.isBlank() || password == null || password.isBlank()) {
            return AuthResult.fail(GENERIC_ERROR);
        }

        final Optional<User> optUser = userRepository.findByEmail(email);

        if (optUser.isEmpty()) {
            // Hash ficticio para igualar tiempo de respuesta (anti timing-attack)
            hashingService.hash(password, hashingService.generateSalt());
            return AuthResult.fail(GENERIC_ERROR);
        }

        final User user = optUser.get();
        final boolean valid = hashingService.verify(
                password, user.getSalt(), user.getPasswordHash()
        );

        if (valid) {
            return AuthResult.ok("Inicio de sesion exitoso. Bienvenido/a, " + user.getEmail() + ".");
        }

        return AuthResult.fail(GENERIC_ERROR);
    }
}
