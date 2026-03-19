public class User {
    private final Email email;
    private final String passwordHash; // Usaremos un String por ahora para el hash

    public User(Email email, String passwordHash) {
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public Email getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
}
