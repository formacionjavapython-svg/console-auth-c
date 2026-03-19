package source.model;

public class User {
    private Email email;
    private Password password;

    public User(Email email, Password password) {
        this.email = email;
        this.password = password;
    }

    public Email getEmail() {
        return email;
    }

    public Password getPassword() {
        return password;
    }
}
