package auth.service;
import auth.security.PasswordHasher;
import auth.security.PasswordPolicy;
import auth.model.User;
import auth.repository.UserRepository;

public class AuthService {
    private PasswordPolicy policy = new PasswordPolicy();
    private UserRepository repo = new UserRepository();
    private PasswordHasher hasher = new PasswordHasher();

    public void register(String email, String password) {
        if (!email.contains("@")) {
            System.out.println("Email inválido, debe contener '@'");
            return;
        }
        if (repo.findByEmail(email) != null) {
            System.out.println("Usuario ya existe");
            return;
        }
        var errors = policy.validate(password, email);

        if (!errors.isEmpty()) {
            for (String error : errors) {
                System.out.println(error);
            }
            return;
        }

        String hash = hasher.hash(password);
        repo.save(new User(email, hash));
        System.out.println("Registro exitoso");
    }

    public void login(String email, String password) {
        User user = repo.findByEmail(email);

        if (user == null || !hasher.verify(password, user.getPasswordHash())) {
            System.out.println("Credenciales incorrectas");
        } else {
            System.out.println("Login exitoso");
        }
    }
}