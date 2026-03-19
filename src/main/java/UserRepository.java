import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Repositorio de usuarios en memoria.
 * No usa base de datos. Los datos se pierden al cerrar la aplicacion (GC).
 * ConcurrentHashMap para thread-safety.
 */
public final class UserRepository {

    private final Map<String, User> users = new ConcurrentHashMap<>();

    public boolean save(final User user) {
        final String key = user.getEmail().toLowerCase();
        if (users.containsKey(key)) {
            return false;
        }
        users.put(key, user);
        return true;
    }

    public Optional<User> findByEmail(final String email) {
        if (email == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(users.get(email.toLowerCase()));
    }

    public boolean existsByEmail(final String email) {
        if (email == null) {
            return false;
        }
        return users.containsKey(email.toLowerCase());
    }
}
