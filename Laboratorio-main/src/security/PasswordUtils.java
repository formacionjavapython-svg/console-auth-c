package security;

import model.PasswordHash;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;

public class PasswordUtils {

    private static final String PEPPER =
            System.getenv().getOrDefault("AUTH_PEPPER_SECRET", "pepper123");

    public static PasswordHash hash(String password) {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);

        byte[] hash = computeHash(password, salt);
        return new PasswordHash(hash, salt);
    }

    public static byte[] computeHash(String password, byte[] salt) {
        try {
            PBEKeySpec spec = new PBEKeySpec(
                    (password + PEPPER).toCharArray(),
                    salt,
                    100000,
                    256
            );

            SecretKeyFactory factory =
                    SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

            return factory.generateSecret(spec).getEncoded();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}