package com.auth.app.repository;

import com.auth.app.model.Email;
import com.auth.app.model.User;
import java.util.Optional;

public interface UserRepository {
    void save(User user);

    Optional<User> findByEmail(Email email);
}
