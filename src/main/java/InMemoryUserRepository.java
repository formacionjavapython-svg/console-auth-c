import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryUserRepository implements UserRepository {
    private final Map<Email, User> users = new HashMap<>();

    @Override
    public void save(User user) {
        users.put(user.getEmail(), user);
        System.out.println("usuario guardado con exito: " + user.getEmail().getValue());
    }

    @Override
    public Optional<User> findByEmail(Email email) {
        return Optional.ofNullable(users.get(email));
    }
}