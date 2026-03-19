package main.java;

import main.java.auth.AuthService;

import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        AuthService authService = new AuthService();
        Scanner scanner = new Scanner(System.in);
        boolean active = true;

        while (active) {
            System.out.println("\nSistema de autenticación");
            System.out.println("1. Registrarse \n2. Iniciar Sesión\n3. Salir");
            System.out.print("Seleccione una opción: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> {
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Password: ");
                    String pass = scanner.nextLine();
                    System.out.println(authService.register(email, pass));
                }
                case "2" -> {
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Password: ");
                    String pass = scanner.nextLine();
                    System.out.println(authService.login(email, pass));
                }
                case "3" -> {
                    active = false;
                    System.out.println("Saliendo del sistema...");
                }
                default -> System.out.println("Opción inválida.");
            }
        }
        scanner.close();
    }
}
