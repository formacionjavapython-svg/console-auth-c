import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryUserRepository implements UserRepository {

    private final Map<Email, User> storage = new HashMap<>();

    @Override
    public void save(User user) {
        storage.put(user.getEmail(), user);
    }

    @Override
    public Optional<User> findByEmail(Email email) {
        return Optional.ofNullable(storage.get(email));
    }
}