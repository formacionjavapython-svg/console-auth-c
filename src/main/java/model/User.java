package main.java.model;

public class User {
    private Email email;
    private HashedPassword passwordHash;

    public User(Email email, HashedPassword password) {
        this.email = email;
        this.passwordHash = password;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public Email getEmail() {
        return this.email;
    }

    public String getHashedPassword() {
        return this.passwordHash.getHashedPasswordString();
    }
}
