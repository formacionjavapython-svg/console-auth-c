import java.util.*;
import java.security.SecureRandom;

public class AuthService {
    private final List<User> users = new ArrayList<>();
    private final List<PasswordRule> rules = new ArrayList<>();

    public AuthService() {
        rules.add(new MinLengthRule(8));
        rules.add(new ContainsNumberRule());
        rules.add(new NoEmailInPasswordRule());
    }

    public List<String> validatePassword(String password, String email) {
        List<String> errors = new ArrayList<>();
        for (PasswordRule rule : rules) {
            String err = rule.validate(password, email);
            if (err != null) errors.add(err);
        }
        return errors;
    }

    public void register(String email, String password) {
        users.add(new User(email, password));
    }

    public Optional<User> findUser(String email) {
        return users.stream().filter(u -> u.getEmail().equalsIgnoreCase(email)).findFirst();
    }

    // MÉTODOS QUE MAIN.JAVA NECESITA PARA COMPILAR
    public byte[] generateSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return salt;
    }

    public String hashPassword(String password, byte[] salt) {
        return "HASHED_" + password; // Simulación para que compile
    }

    public boolean verifyPassword(String password, String hash, byte[] salt) {
        return hash.equals("HASHED_" + password);
    }
}
