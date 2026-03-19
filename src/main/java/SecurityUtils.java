import java.security.SecureRandom;
import java.util.Base64;
import java.util.Objects;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class SecurityUtils {
    // El "Pepper" solicitado en la diapositiva
    private static final String AUTH_PEPPER_SECRET = "C34_SECRET_PEPPER_2026";
    private static final int ITERATIONS = 100000;
    private static final int KEY_LENGTH = 256;

    public static String hashPassword(String password) {
        Objects.requireNonNull(password, "Password no puede ser nulo");
        try {
            byte[] salt = new byte[16];
            SecureRandom.getInstance("SHA1PRNG").nextBytes(salt);
            
            // Concatenamos el Pepper antes de hashear
            String combined = password + AUTH_PEPPER_SECRET;
            
            PBEKeySpec spec = new PBEKeySpec(combined.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            
            byte[] hash = factory.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("Error en el proceso de seguridad", e);
        }
    }

    public static boolean verifyPassword(String password, String storedHash) {
        if (password == null || storedHash == null) return false;
        try {
            String[] parts = storedHash.split(":");
            byte[] salt = Base64.getDecoder().decode(parts[0]);
            byte[] hash = Base64.getDecoder().decode(parts[1]);

            String combined = password + AUTH_PEPPER_SECRET;
            PBEKeySpec spec = new PBEKeySpec(combined.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] testHash = factory.generateSecret(spec).getEncoded();

            // Comparación Constant-Time (evita timing attacks)
            return java.security.MessageDigest.isEqual(hash, testHash);
        } catch (Exception e) {
            return false;
        }
    }
}