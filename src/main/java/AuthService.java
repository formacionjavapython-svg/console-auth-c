import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class AuthService {

    // Repositorio en memoria
    private List<User> userRepository = new ArrayList<>();

    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;
    private static final String ALGORITHM = "PBKDF2WithHmacSHA1";

    
    public void register(String email, String password) {
        
        Objects.requireNonNull(email, "El email no puede ser null");
        Objects.requireNonNull(password, "El password no puede ser null");

        if (isPasswordStrong(password)) {
            String salt = generateSalt();
            String hash = hashPassword(password, salt);
            userRepository.add(new User(email, hash, salt));
        }
    }

    
    public boolean login(String email, String password) {
        
        return findUser(email)
            .map(user -> verifyPassword(password, user.getPasswordHash(), user.getSalt()))
            .orElse(false);
    }

    
    public List<String> validatePassword(String password, String email) {
        List<String> errors = new ArrayList<>();
        if (!isPasswordStrong(password)) {
            errors.add("La contraseña no cumple con la política de seguridad (8+ caracteres, Mayúscula, Número).");
        }
        return errors;
    }

    
    public Optional<User> findUser(String email) {
        return userRepository.stream()
            .filter(u -> u.getEmail().equals(email))
            .findFirst();
    }

    //  MÉTODOS DE SEGURIDAD (PBKDF2) 

    public static boolean isPasswordStrong(String password) {
        return password != null && Pattern.matches(PASSWORD_PATTERN, password);
    }

    public static String generateSalt() {
        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public static String hashPassword(String password, String salt) {
        char[] passwordChars = password.toCharArray();
        byte[] saltBytes = Base64.getDecoder().decode(salt);
        PBEKeySpec spec = new PBEKeySpec(passwordChars, saltBytes, ITERATIONS, KEY_LENGTH);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM);
            byte[] hash = skf.generateSecret(spec).getEncoded();
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Error al cifrar contraseña", e);
        }
    }

    public static boolean verifyPassword(String inputPassword, String storedHash, String salt) {
        String newHash = hashPassword(inputPassword, salt);
        return newHash.equals(storedHash);
    }
}