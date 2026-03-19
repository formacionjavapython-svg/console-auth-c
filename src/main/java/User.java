import java.util.Objects;

/**
 * Modelo inmutable de usuario (POO).
 * Almacena email, hash de contrasena y salt unico.
 */
public final class User {

    private final String email;
    private final String passwordHash;
    private final String salt;

    public User(final String email, final String passwordHash, final String salt) {
        this.email = Objects.requireNonNull(email, "Email no puede ser null");
        this.passwordHash = Objects.requireNonNull(passwordHash, "Hash no puede ser null");
        this.salt = Objects.requireNonNull(salt, "Salt no puede ser null");
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getSalt() {
        return salt;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof User other)) {
            return false;
        }
        return email.equalsIgnoreCase(other.email);
    }

    @Override
    public int hashCode() {
        return email.toLowerCase().hashCode();
    }

    @Override
    public String toString() {
        return "User{email='" + email + "'}";
    }
}
