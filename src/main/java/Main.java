import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static List<User> userRepository = new ArrayList<>();
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("--- BIENVENIDO ---");

        while (running) {
            System.out.println("\nSeleccione una opción:");
            System.out.println("1 Register");
            System.out.println("2 Login");
            System.out.println("3 Exit");
            System.out.print("> ");

            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    System.out.println("--- Registro de Usuario ---");
                    System.out.print("Ingrese email: ");
                    String email = scanner.nextLine();
                    
                    System.out.print("Ingrese password: ");
                    String password = scanner.nextLine();

                    if (AuthService.isPasswordStrong(password)) {
                        String salt = AuthService.generateSalt();
                        String hash = AuthService.hashPassword(password, salt);
                        
                        User newUser = new User(email, hash, salt);
                        userRepository.add(newUser);
                        
                        System.out.println("¡Usuario registrado con éxito!");
                    } else {
                        System.out.println("Error: Password débil (mínimo 8 caracteres, 1 número, 1 mayúscula).");
                    }
                    break;
                case "2":
                    System.out.println("--- Inicio de Sesión ---");
                    System.out.print("Email: ");
                    String loginEmail = scanner.nextLine();
                    
                    System.out.print("Password: ");
                    String loginPass = scanner.nextLine();

                    // Buscamos al usuario
                    User foundUser = null;
                    for (User u : userRepository) {
                        if (u.getEmail().equals(loginEmail)) {
                            foundUser = u;
                            break;
                        }
                    }

                    if (foundUser != null) {
                        // Verificamos el hash usando el salt del usuario encontrado
                        boolean isSuccess = AuthService.verifyPassword(loginPass, foundUser.getPasswordHash(), foundUser.getSalt());
                        
                        if (isSuccess) {
                            System.out.println("¡Bienvenido! Has iniciado sesión correctamente.");
                        } else {
                            // Criterio: Mensaje de error genérico 
                            System.out.println("Error: Credenciales inválidas.");
                        }
                    } else {
                        // Criterio: Mensaje de error genérico
                        System.out.println("Error: Credenciales inválidas.");
                    }
                    break;
                case "3":
                    System.out.println("Saliendo del sistema ¡Adiós!");
                    running = false;
                    break;
                default:
                    System.out.println("Opción no válida, intenta de nuevo.");
            }
        }
        scanner.close();
    }
}