import java.util.ArrayList;
import java.util.List;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;

public class AuthService {
    private List<PasswordRule> rules = new ArrayList<>();
    private static final String PEPPER = "Railway_System_2026";

    public AuthService() {
        rules.add(new MinLengthRule());
        rules.add(new ContainsNumberRule());
        rules.add(new NoEmailInPasswordRule());
        rules.add(new ContainsUppercaseRule());
    }

    public List<String> validatePassword(String password, String email) {
        List<String> violations = new ArrayList<>();
        for (PasswordRule rule : rules) {
            String error = rule.validate(password, email);
            if (error != null) violations.add(error);
        }
        return violations;
    }

    public String hashPassword(String password, byte[] salt) throws Exception {
        KeySpec spec = new PBEKeySpec((password + PEPPER).toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        return Base64.getEncoder().encodeToString(factory.generateSecret(spec).getEncoded());
    }

    public boolean verifyPassword(String input, String stored, byte[] salt) throws Exception {
        return hashPassword(input, salt).equals(stored);
    }

    public byte[] generateSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return salt;
    }
}
