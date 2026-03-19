package source.model;

public class Password {
    private String password;

    public Password(String password) {
        if (password == null || password.isBlank() || password.length() < 8)
            throw new IllegalArgumentException("Password inválido");
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public boolean matches(String rawPassword) {
        return password.equals(rawPassword);
    }
}
