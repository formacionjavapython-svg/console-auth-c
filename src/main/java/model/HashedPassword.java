package main.java.model;

public class HashedPassword {
    private final String hashedPassword;
    
    public HashedPassword(String password) {
        this.hashedPassword = hashPassword(password);
    }

    public String getHashedPasswordString() {
        return hashedPassword;
    }
    
    private String hashPassword(String password) {
        return password.hashCode() + "";
    }
}
