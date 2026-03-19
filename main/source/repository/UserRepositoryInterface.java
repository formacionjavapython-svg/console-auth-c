package source.repository;

import source.model.Email;
import source.model.User;

import java.util.Optional;

public interface UserRepositoryInterface {

    void addUser(User user);
    Optional<User> searchByEmail(Email email);
}

