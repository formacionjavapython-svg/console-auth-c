import java.security.MessageDigest;

public class PasswordService {

    public String hash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password.getBytes());

            StringBuilder hexString = new StringBuilder();

            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }

            return hexString.toString();

        } catch (Exception e) {
            throw new RuntimeException("Error al generar hash", e);
        }
    }
}