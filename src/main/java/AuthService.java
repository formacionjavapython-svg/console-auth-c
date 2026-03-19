import java.util.List;

public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Registro de usuario
    public boolean register(String emailStr, String password) {
        Email email = new Email(emailStr);

        // Verifica si el usuario ya existe
        if (userRepository.findByEmail(email).isPresent()) {
            System.out.println("El usuario ya existe");
            return false;
        }

        // Política de contraseña
        PasswordPolicy policy = new PasswordPolicy();
        policy.addRule(new MinLengthRule(8));
        policy.addRule(new ContainsNumberRule());
        policy.addRule(new NoEmailInPasswordRule());

        // Validar la contraseña
        List<String> violations = policy.validate(password, email);
        if (!violations.isEmpty()) {
            System.out.println("Errores en la contraseña:");
            for (String msg : violations) {
                System.out.println("- " + msg);
            }
            return false; // No registra si falla alguna regla
        }

        // Si pasa todas las reglas, se registra el usuario
        User user = new User(email, password);
        userRepository.save(user);
        return true;
    }

    // Login de usuario
    public boolean login(String emailStr, String password) {
        Email email = new Email(emailStr);
        return userRepository.findByEmail(email)
                .map(user -> user.getPassword().equals(password))
                .orElse(false);
    }
}