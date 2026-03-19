package com.consoleauthlab.console;

import com.consoleauthlab.repository.UserRepository;
import com.consoleauthlab.service.AuthService;
import com.consoleauthlab.service.HashService;

import java.util.Scanner;

/**
 * Aplicación de consola para autenticación de usuarios.
 */
public class ConsoleApp {
    
    private final AuthService authService;
    private final Scanner scanner;
    
    public ConsoleApp() {
        UserRepository userRepository = new UserRepository();
        HashService hashService = new HashService();
        this.authService = new AuthService(userRepository, hashService);
        this.scanner = new Scanner(System.in);
    }
    
    public void run() {
        System.out.println("=== SISTEMA DE AUTENTICACIÓN CONSOLA ===");
        System.out.println("Bienvenido al sistema de autenticación seguro\n");
        
        boolean running = true;
        
        while (running) {
            showMainMenu();
            String option = scanner.nextLine().trim();
            
            switch (option) {
                case "1":
                    registerUser();
                    break;
                case "2":
                    loginUser();
                    break;
                case "3":
                    changePassword();
                    break;
                case "4":
                    System.out.println("\nSaliendo del sistema...");
                    running = false;
                    break;
                default:
                    System.out.println("\nOpción no válida. Por favor, seleccione 1-4.");
            }
        }
        
        scanner.close();
        System.out.println("Sistema finalizado. ¡Hasta pronto!");
    }
    
    private void showMainMenu() {
        System.out.println("\n=== MENÚ PRINCIPAL ===");
        System.out.println("1. Registrarse (nuevo usuario)");
        System.out.println("2. Iniciar sesión (login)");
        System.out.println("3. Cambiar contraseña");
        System.out.println("4. Salir del sistema");
        System.out.print("Seleccione una opción (1-4): ");
    }
    
    private void registerUser() {
        System.out.println("\n=== REGISTRO DE NUEVO USUARIO ===");
        
        System.out.print("Ingrese su email: ");
        String email = scanner.nextLine().trim();
        
        System.out.print("Ingrese su contraseña: ");
        String password = scanner.nextLine().trim();
        
        boolean success = authService.register(email, password);
        
        if (success) {
            System.out.println("\n¡Registro exitoso!");
            System.out.println("Usuario registrado con email: " + email);
        } else {
            System.out.println("\nError en el registro:");
            System.out.println("- El email puede ser inválido o ya estar registrado");
            System.out.println("- La contraseña no cumple con las políticas de seguridad");
        }
    }
    
    private void loginUser() {
        System.out.println("\n=== INICIO DE SESIÓN ===");
        
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        
        System.out.print("Contraseña: ");
        String password = scanner.nextLine().trim();
        
        boolean success = authService.login(email, password);
        
        if (success) {
            System.out.println("\n¡Autenticación exitosa!");
            System.out.println("Bienvenido al sistema, usuario: " + email);
        } else {
            System.out.println("\nAutenticación fallida: Credenciales incorrectas.");
        }
    }
    
    private void changePassword() {
        System.out.println("\n=== CAMBIO DE CONTRASEÑA ===");
        
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        
        System.out.print("Contraseña actual: ");
        String oldPassword = scanner.nextLine().trim();
        
        System.out.print("Nueva contraseña: ");
        String newPassword = scanner.nextLine().trim();
        
        boolean success = authService.changePassword(email, oldPassword, newPassword);
        
        if (success) {
            System.out.println("\n¡Contraseña cambiada exitosamente!");
        } else {
            System.out.println("\nError al cambiar contraseña:");
            System.out.println("- Credenciales incorrectas");
            System.out.println("- Nueva contraseña no cumple políticas");
        }
    }
    
    public static void main(String[] args) {
        ConsoleApp app = new ConsoleApp();
        app.run();
    }
}