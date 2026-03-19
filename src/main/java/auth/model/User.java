package auth.model;
public class User {

    public String getEmail() {
        return email;
    }
    private String email;
    private String passwordHash;

    public User(String email, String passwordHash) {
        this.email = email;
        this.passwordHash = passwordHash;
    }
    public String getPasswordHash() {
        return passwordHash;
    }
}