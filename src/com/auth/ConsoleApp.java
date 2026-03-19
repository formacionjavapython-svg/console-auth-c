package com.auth;

import com.auth.service.AuthService;

import java.util.Scanner;

public class ConsoleApp {

    private final AuthService authService;
    private final Scanner scanner;

    public ConsoleApp(AuthService authService) {
        this.authService = authService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {

        boolean running = true;

        while (running) {

            System.out.println("\n--- MENU ---");
            System.out.println("1. Registrar");
            System.out.println("2. Login");
            System.out.println("3. Salir");
            System.out.print("Selecciona una opción: ");

            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    register();
                    break;
                case "2":
                    login();
                    break;
                case "3":
                    running = false;
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private void register() {
        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        authService.register(email, password);
    }

    private void login() {
        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        boolean success = authService.login(email, password);

        if (success) {
            System.out.println("Login exitoso");
        } else {
            System.out.println("Credenciales inválidas");
        }
    }
}