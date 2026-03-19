package repository;

import java.util.Optional;
import model.Email;
import model.User;

public interface UserRepository {
    void save(User user);
    Optional<User> findByEmail(Email email);
}