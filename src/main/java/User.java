public class User {
    private final Email email;
    private final String password;

    public User(Email email, String password) {
        this.email = email;
        this.password = password;
    }

    public Email getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{" +
                "email=" + email +
                '}';
    }
}
