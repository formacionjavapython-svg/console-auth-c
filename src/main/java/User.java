import java.util.Objects;

public class User {


    private String email;
    private String passwordHash;

    public User(String email, String passwordHash) {
        this.email = Objects.requireNonNull(email, "email no puede ser null");
        this.passwordHash = Objects.requireNonNull(passwordHash, "password no puede ser null");
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
}
