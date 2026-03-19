public class User {
    private String email;
    private String passwordHash; 
    private String salt;         

    // Registro de un nuevo usuario
    public User(String email, String passwordHash, String salt) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.salt = salt;
    }

    // Valida el login después
    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getSalt() {
        return salt;
    }
}