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
    public static void main(String[] args) {

    System.out.println("--- INICIANDO TEST RUNNER PERSONALIZADO ---");

    testRegisterAndLoginSuccess();
    testPasswordPolicyBlocks();

    System.out.println("--- TODAS LAS PRUEBAS FINALIZADAS ---");
  }

  static AuthService buildAuthService() {

    UserRepository repo = new InMemoryUserRepository();

    PasswordPolicy policy = new PasswordPolicy(List.of(
        new MinLengthRule(),
        new ContainsNumberRule(),
        new NoEmailInPasswordRule()
    ));

    return new AuthService(repo, policy);
  }

  static void testRegisterAndLoginSuccess() {

    AuthService auth = buildAuthService();

    List<String> errors = auth.register("irvin@test.com", "Password2026!");

    boolean loginSuccess = auth.login("irvin@test.com", "Password2026!");

    System.out.println("Test Registro + Login: "
        + (errors.isEmpty() && loginSuccess ? "PASÓ" : "FALLÓ"));
  }

  static void testPasswordPolicyBlocks() {

    AuthService auth = buildAuthService();

    List<String> errors = auth.register("irvin@test.com", "123");

    System.out.println("Test Política Bloquea: "
        + (!errors.isEmpty() ? "PASÓ" : "FALLÓ"));
  }
}
