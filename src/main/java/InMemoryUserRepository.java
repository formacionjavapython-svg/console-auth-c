import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryUserRepository implements UserRepository {
    private final Map<String, User> database = new HashMap<>();

    @Override
    public void save(User user) {
        database.put(user.getEmail().getValue(), user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(database.get(email));
    }
}