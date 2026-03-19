package com.auth.app;
import com.auth.app.repository.InMemoryUserRepository;
import com.auth.app.repository.UserRepository;
import com.auth.app.service.AuthService;
import com.auth.app.service.PasswordPolicy;
import com.auth.app.validation.ContainsNumberRule;
import com.auth.app.validation.MinLengthRule;
import com.auth.app.validation.NoEmailInPasswordRule;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            UserRepository repo = new InMemoryUserRepository();
            
            PasswordPolicy policy = new PasswordPolicy(List.of(
                    new MinLengthRule(),
                    new ContainsNumberRule(),
                    new NoEmailInPasswordRule()
            ));
            
            AuthService auth = new AuthService(repo, policy);
            
            boolean running = true;
            
            while (running) {
                
                showMenu();
                
                String option = sc.nextLine();
                
                switch (option) {
                    case "1" -> handleRegister(sc, auth);
                    case "2" -> handleLogin(sc, auth);
                    case "3" -> {
                        running = false;
                        System.out.println("Cerrando sistema...");
                    }
                    default -> System.out.println("Opción inválida.");
                }
            }   }
  }

  private static void showMenu() {
    System.out.println("\n--- SISTEMA DE AUTENTICACIÓN ---");
    System.out.println("1. Registrarse");
    System.out.println("2. Iniciar Sesión");
    System.out.println("3. Salir");
    System.out.print("Opción: ");
  }

  private static void handleRegister(Scanner sc, AuthService auth) {

    System.out.print("Email: ");
    String email = sc.nextLine().trim().toLowerCase();

    System.out.print("Password: ");
    String password = sc.nextLine();

    List<String> errors = auth.register(email, password);

    if (errors.isEmpty()) {
      System.out.println("[OK] Usuario registrado con éxito.");
    } else {
      System.out.println("[ERROR] La contraseña es inválida:");
      for (String err : errors) {
        System.out.println(" - " + err);
      }
    }
  }

  private static void handleLogin(Scanner sc, AuthService auth) {

    System.out.print("Email: ");
    String email = sc.nextLine().trim().toLowerCase();

    System.out.print("Password: ");
    String password = sc.nextLine();

    boolean success = auth.login(email, password);

    if (success) {
      System.out.println("\n[BIENVENIDO] ¡Acceso concedido!");
    } else {
      System.out.println("\n[ERROR] Credenciales inválidas.");
    }
    }
}
