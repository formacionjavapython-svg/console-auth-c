package com.auth.repository;

import com.auth.domain.Email;
import com.auth.domain.User;

import java.util.Optional;

public interface UserRepository {

    void save(User user);

    Optional<User> findByEmail(Email email);
}