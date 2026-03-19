import java.util.HashMap;
import java.util.Objects;

public class AuthService {

    private HashMap<String, User> users = new HashMap<>();
    private PasswordService passwordService = new PasswordService();

    public void register(String email, String password) {

        Objects.requireNonNull(email, "email no puede ser null");
        Objects.requireNonNull(password, "password no puede ser null");

        String hash = passwordService.hash(password);

        User user = new User(email, hash);
        users.put(email, user);

        System.out.println("Usuario registrado");
    }

    public boolean login(String email, String password) {

        Objects.requireNonNull(email);
        Objects.requireNonNull(password);

        User user = users.get(email);

        if (user == null) {
            return false;
        }

        String hash = passwordService.hash(password);

        return user.getPasswordHash().equals(hash);
    }
}
