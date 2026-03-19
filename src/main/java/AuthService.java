import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.regex.Pattern;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;

public class AuthService {
    private static final String PEPPER = "Railway_System_2026"; 
    private static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*[0-9]).{8,}$";

    public boolean validatePasswordPolicy(String password) {
        return Pattern.compile(PASSWORD_PATTERN).matcher(password).matches();
    }

    public String hashPassword(String password, byte[] salt) throws Exception {
        KeySpec spec = new PBEKeySpec((password + PEPPER).toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = factory.generateSecret(spec).getEncoded();
        return Base64.getEncoder().encodeToString(hash);
    }

    // NUEVO: Método para verificar si la contraseña ingresada es correcta
    public boolean verifyPassword(String inputPassword, String storedHash, byte[] salt) throws Exception {
        String newHash = hashPassword(inputPassword, salt);
        return newHash.equals(storedHash);
    }

    public byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }
}
