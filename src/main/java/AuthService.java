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
        Objects.requireNonNull(password);
        Objects.requireNonNull(email);
        List<String> errors = new ArrayList<>();
        for (PasswordRule rule : rules) {
            String error = rule.validate(password, email);
            if (error != null) errors.add(error);
        }
        return errors;
    }

    public Optional<User> findUser(String email) {
        return users.stream().filter(u -> u.getEmail().equalsIgnoreCase(email)).findFirst();
    }

    public void register(String email, String password) {
        users.add(new User(email, password));
    }

    // Métodos que Main.java necesita para no dar error:
    public byte[] generateSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return salt;
    }

    public String hashPassword(String pass, byte[] salt) { return "HASHED_" + pass; }
    
    public boolean verifyPassword(String pass, String hash, byte[] salt) {
        return hash.equals("HASHED_" + pass);
    }
}
