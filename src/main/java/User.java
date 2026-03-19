C38-Tania-Mendez
import java.util.Objects;
import rules.Email;

public class User {
  private final Email email;
  private final String passwordHash;

  public User(Email email, String passwordHash) {
    this.email = Objects.requireNonNull(email, "el objeto Email no puede ser nulo");
    this.passwordHash = Objects.requireNonNull(passwordHash, "el passwordHash no puede ser nulo");
  }

  public Email getEmail() {
    return email;
  }

  public String getPasswordHash() {
    return passwordHash;
  }
}

public class User {
    private String email;
    private String passwordHash;

    public User(String email, String passwordHash) {
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
}
main
