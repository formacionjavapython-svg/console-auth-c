import java.security.MessageDigest;

public class PasswordHash {
    private final byte[] hash;
    private final byte[] salt;

    public PasswordHash(byte[] hash, byte[] salt) {
        this.hash = hash;
        this.salt = salt;
    }

    public boolean matches(String password, String pepper, PBKDF2Service service) throws Exception {
        byte[] attempt = service.computeHash(password, pepper, this.salt);

        return MessageDigest.isEqual(this.hash, attempt);
    }
}