package com.auth.app.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.auth.app.model.Email;
import com.auth.app.model.User;

public class InMemoryUserRepository implements UserRepository{
    
    private final Map<Email, User> users = new HashMap<>();

    @Override
    public void save(User user) {
        if (users.containsKey(user.getEmail())) {
            throw new IllegalArgumentException("User already exists");
        }
        users.put(user.getEmail(), user);
    }

    @Override
    public Optional<User> findByEmail(Email email) {
        return Optional.ofNullable(users.get(email));
    } 
}
