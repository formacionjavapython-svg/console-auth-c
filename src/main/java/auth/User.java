package main.java.auth;

public class User {
    private final String email;
    private final String password;
    private final byte[] salt;

    public User(String email, String passwordHash, byte[] salt) {
        this.email = email;
        this.password = passwordHash;
        this.salt = salt;
    }

    public String getPasswordHash() {
        return password;
    }

    public byte[] getSalt() {
        return salt;
    }
}
