package com.consoleauthlab.repository;

import com.consoleauthlab.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Repositorio para almacenar y recuperar usuarios.
 * Implementación en memoria.
 */
public class UserRepository {
    
    private final Map<String, User> users = new HashMap<>();
    
    public void save(User user) {
        users.put(user.getEmail().getValue(), user);
    }
    
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(users.get(email));
    }
    
    public boolean existsByEmail(String email) {
        return users.containsKey(email);
    }
    
    public void deleteByEmail(String email) {
        users.remove(email);
    }
    
    public int count() {
        return users.size();
    }
    
    public void clear() {
        users.clear();
    }
}