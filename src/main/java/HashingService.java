import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

/**
 * Servicio de hashing con PBKDF2WithHmacSHA1 (Java estandar).
 * Criptografia avanzada:
 *   - Salt aleatorio: cada usuario tiene un salt unico de 16 bytes
 *     para evitar Rainbow Tables.
 *   - Pepper: capa extra de seguridad estatica en el servidor.
 *   - 65,536 iteraciones para dificultar ataques de fuerza bruta.
 *
 * No utiliza librerias externas. Solo javax.crypto de Java.
 */
public final class HashingService {

    private static final String ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final int ITERATIONS = 65_536;
    private static final int KEY_LENGTH = 256;
    private static final int SALT_LENGTH = 16;
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private final String pepper;

    public HashingService(final String pepper) {
        this.pepper = (pepper != null) ? pepper : "";
    }

    /**
     * Genera un salt aleatorio de 16 bytes codificado en Base64.
     *
     * @return salt en Base64
     */
    public String generateSalt() {
        final byte[] salt = new byte[SALT_LENGTH];
        SECURE_RANDOM.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    /**
     * Genera el hash PBKDF2 de la contrasena con salt unico y pepper.
     *
     * @param password contrasena en texto plano
     * @param salt     salt unico del usuario (Base64)
     * @return hash en Base64
     */
    public String hash(final String password, final String salt) {
        try {
            final String seasoned = password + pepper;
            final byte[] saltBytes = Base64.getDecoder().decode(salt);
            final PBEKeySpec spec = new PBEKeySpec(
                    seasoned.toCharArray(), saltBytes, ITERATIONS, KEY_LENGTH
            );
            final SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
            final byte[] hashBytes = factory.generateSecret(spec).getEncoded();
            spec.clearPassword();
            return Base64.getEncoder().encodeToString(hashBytes);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new SecurityException("Error al generar hash: " + e.getMessage(), e);
        }
    }

    /**
     * Verifica si la contrasena coincide con el hash almacenado.
     * Usa comparacion en tiempo constante para prevenir timing attacks.
     *
     * @param password contrasena en texto plano
     * @param salt     salt del usuario
     * @param expected hash almacenado
     * @return true si coincide
     */
    public boolean verify(final String password, final String salt, final String expected) {
        final String computed = hash(password, salt);
        return constantTimeEquals(computed, expected);
    }

    /**
     * Comparacion en tiempo constante para prevenir timing attacks.
     */
    private boolean constantTimeEquals(final String a, final String b) {
        if (a.length() != b.length()) {
            return false;
        }
        int result = 0;
        for (int i = 0; i < a.length(); i++) {
            result |= a.charAt(i) ^ b.charAt(i);
        }
        return result == 0;
    }
}
