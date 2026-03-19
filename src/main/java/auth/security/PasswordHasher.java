package auth.security;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordHasher {

    private static final String PEPPER = "mi_pepper_secreto";
    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 256;

    public String hash(String password) {
        try {
            // generar salt
            byte[] salt = new byte[16];
            new SecureRandom().nextBytes(salt);

            // combinar password + pepper
            String finalPassword = password + PEPPER;

            PBEKeySpec spec = new PBEKeySpec(
                    finalPassword.toCharArray(),
                    salt,
                    ITERATIONS,
                    KEY_LENGTH
            );

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = factory.generateSecret(spec).getEncoded();

            // guardar salt + hash
            return Base64.getEncoder().encodeToString(salt) + ":" +
                    Base64.getEncoder().encodeToString(hash);

        } catch (Exception e) {
            throw new RuntimeException("Error al hashear password", e);
        }
    }
    public boolean verify(String password, String storedHash) {
        try {
            String[] parts = storedHash.split(":");

            byte[] salt = Base64.getDecoder().decode(parts[0]);
            byte[] originalHash = Base64.getDecoder().decode(parts[1]);

            String finalPassword = password + PEPPER;

            PBEKeySpec spec = new PBEKeySpec(
                    finalPassword.toCharArray(),
                    salt,
                    ITERATIONS,
                    KEY_LENGTH
            );

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] testHash = factory.generateSecret(spec).getEncoded();

            return java.util.Arrays.equals(originalHash, testHash);

        } catch (Exception e) {
            throw new RuntimeException("Error al verificar password", e);
        }
    }
}