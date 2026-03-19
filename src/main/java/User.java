import java.util.Objects;

public class User {
    private final Email email;
    private final String passwordHash;

    public User(Email email, String passwordHash) {
        // Validación defensiva
        this.email = Objects.requireNonNull(email, "Email obligatorio");
        this.passwordHash = Objects.requireNonNull(passwordHash, "Hash obligatorio");
    }

    public Email getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
}