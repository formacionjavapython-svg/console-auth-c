package com.auth.app.service;

import com.auth.app.model.Email;
import com.auth.app.model.PasswordHash;
import com.auth.app.model.User;
import com.auth.app.repository.UserRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class AuthService {

private final UserRepository userRepository;
  private final PasswordPolicy passwordPolicy;
  private final String pepper;

  public AuthService(UserRepository userRepository, PasswordPolicy passwordPolicy) {

    this.userRepository = Objects.requireNonNull(userRepository);
    this.passwordPolicy = Objects.requireNonNull(passwordPolicy);

    this.pepper = System.getenv("AUTH_PEPPER_SECRET");

    if (pepper == null) {
      throw new IllegalStateException("Missing AUTH_PEPPER_SECRET");
    }
  }

  public List<String> register(String emailRaw, String password) {

    Objects.requireNonNull(emailRaw);
    Objects.requireNonNull(password);

    Email email = new Email(emailRaw);

    List<String> errors = passwordPolicy.validate(password, email);

    if (!errors.isEmpty()) {
      return errors;
    }

    if (userRepository.findByEmail(email).isPresent()) {
      return List.of("Invalid credentials");
    }

    PasswordHash hash = PasswordHash.create(password, pepper);

    User user = new User(email, hash);

    userRepository.save(user);

    return List.of();
  }

  public boolean login(String emailRaw, String password) {

    Objects.requireNonNull(emailRaw);
    Objects.requireNonNull(password);

    Email email = new Email(emailRaw);

    Optional<User> userOpt = userRepository.findByEmail(email);

    if (userOpt.isEmpty()) {
      return false;
    }

    User user = userOpt.get();

    return user.getHash().matches(password, pepper);
  }
}
