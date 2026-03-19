public class User {
    private final Email email;
    private final PasswordHash passwordHash;

    public User(Email email, PasswordHash passwordHash) {
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public Email getEmail() { return email; }
    public PasswordHash getPasswordHash() { return passwordHash; }
}