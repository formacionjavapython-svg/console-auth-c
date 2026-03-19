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

        // Crea usuario y lo guarda
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