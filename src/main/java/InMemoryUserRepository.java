import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Objects;
import rules.Email;

public class InMemoryUserRepository implements UserRepository {
  private final Map<Email, User> users = new HashMap<>();

  @Override
  public void save(User user) {
    // Validación Defensiva
    Objects.requireNonNull(user, "no se puede guardar un usuario nulo");
    users.put(user.getEmail(), user);
    System.out.println("usuario guardado con exito: " + user.getEmail().getValue());
  }

  @Override
  public Optional<User> findByEmail(Email email) {
    Objects.requireNonNull(email, "el email de busqueda no puede ser nulo");
    return Optional.ofNullable(users.get(email));
  }
}