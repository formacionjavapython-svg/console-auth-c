package main.java.auth;

public class user {
    private final String email;
    private final String password;
    private final byte[] salt;

    public user(String email, String passwordHash, byte[] salt) {
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
