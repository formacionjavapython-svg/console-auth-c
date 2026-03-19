package com.auth.app;

import com.auth.app.repository.InMemoryUserRepository;
import com.auth.app.repository.UserRepository;
import com.auth.app.service.AuthService;
import com.auth.app.service.PasswordPolicy;
import com.auth.app.validation.ContainsNumberRule;
import com.auth.app.validation.MinLengthRule;
import com.auth.app.validation.NoEmailInPasswordRule;
import java.util.List;


public class AuthServiceTest {
  private AuthService buildService() {

    UserRepository repo = new InMemoryUserRepository();

    PasswordPolicy policy = new PasswordPolicy(List.of(
        new MinLengthRule(),
        new ContainsNumberRule(),
        new NoEmailInPasswordRule()
    ));

    return new AuthService(repo, policy);
  }

  public String testRegisterSuccess() {

    AuthService auth = buildService();

    List<String> errors = auth.register("test@mail.com", "Password2026!");

    boolean login = auth.login("test@mail.com", "Password2026!");

    return errors.isEmpty() && login
        ? "Register + Login Exitoso: PASÓ"
        : "Register + Login Exitoso: FALLÓ";
  }

  public String testLoginFailure() {

    AuthService auth = buildService();

    auth.register("test@mail.com", "Password2026!");

    boolean login = auth.login("test@mail.com", "WrongPass123");

    return !login
        ? "Login con password incorrecto: PASÓ"
        : "ogin con password incorrecto: FALLÓ";
  }

  public String testLoginSuccess() {

    AuthService auth = buildService();

    auth.register("test@mail.com", "Password2026!");

    boolean login = auth.login("test@mail.com", "Password2026!");

    return login
        ? "Login correcto: PASÓ"
        : "Login correcto: FALLÓ";
  }

  public String testPolicyRejects() {

    AuthService auth = buildService();

    List<String> errors = auth.register("test@mail.com", "123");

    return !errors.isEmpty()
        ? "Password policy bloquea: PASÓ"
        : "Password policy bloquea: FALLÓ";
  }
}
