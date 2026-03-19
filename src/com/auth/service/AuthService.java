package com.auth.service;

import com.auth.domain.Email;
import com.auth.domain.PasswordHash;
import com.auth.domain.User;
import com.auth.policy.PasswordPolicy;
import com.auth.repository.UserRepository;

public class AuthService {

    private final UserRepository userRepository;
    private final PasswordPolicy passwordPolicy;

    public AuthService(UserRepository userRepository, PasswordPolicy passwordPolicy) {
        this.userRepository = userRepository;
        this.passwordPolicy = passwordPolicy;
    }

    public void register(String emailStr, String password) {

        Email email = new Email(emailStr);

        var errors = passwordPolicy.validate(password);

        if (!errors.isEmpty()) {
            System.out.println("Password inválido:");
            for (String error : errors) {
                System.out.println("- " + error);
            }
            return;
        }

        PasswordHash passwordHash = new PasswordHash(password);

        User user = new User(email, passwordHash);

        userRepository.save(user);

        System.out.println("Usuario registrado correctamente");
    }

    public boolean login(String emailStr, String password) {

        Email email = new Email(emailStr);

        var userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            return false;
        }

        User user = userOptional.get();

        return user.getPasswordHash().matches(password);
    }
}