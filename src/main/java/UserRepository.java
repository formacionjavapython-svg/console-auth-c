import java.util.Optional;

import rules.Email;

public interface UserRepository {
    void save(User user);
    Optional<User> findByEmail(Email email);
}