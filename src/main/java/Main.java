package main.java;

import java.util.LinkedList;
import java.util.Scanner;

import main.java.model.User;
import main.java.service.AuthService;

public class Main {
    public static AuthService authService = new AuthService();

    public static void main(String args[]) {
        menu();
    }

    public static void menu() {
        Scanner sc = new Scanner(System.in);
        String selectedOption = "";
        System.out.println("Bienvenido al sistema de autenticación.");
        do {
            System.out.println("1. Iniciar sesión\n2. Registrarse\n3. Cerrar Sesión\n4. Salir\n--------------------\n");
            selectedOption = sc.nextLine();
            String[] credentials;
            LinkedList<String> status;
            User currentUser;
            switch (selectedOption) {
                case "1":
                    // login
                    credentials = enterCredentials(sc);
                    status = authService.login(credentials[0], credentials[1]);
                    if (status.size() > 0) {
                        System.out.println("Ocurrieron " + status.size() + " errores al intentar iniciar sesión.");
                        for (String error : status) {
                            System.out.println("Error: " + error);
                        }
                        break;
                    }
                    currentUser = authService.getCurrentUser();
                    System.out.println("Usuario logueado: " + (currentUser != null ? currentUser.getEmail().getEmail() : "Ninguno"));
                    break;
                case "2":
                    // register
                    credentials = enterCredentials(sc);
                    status = authService.register(credentials[0], credentials[1]);
                    if (status.size() > 0) {
                        System.out.println("Ocurrieron " + status.size() + " errores al intentar registrarse.");
                        for (String error : status) {
                            System.out.println("Error: " + error);
                        }
                        break;
                    }
                    currentUser = authService.getCurrentUser();
                    System.out.println("Usuario registrado: "+(currentUser!=null ? currentUser.getEmail().getEmail() : "Ninguno"));
                    break;
                case "3":
                    authService.logout();
                    System.out.println("Sesión cerrada. Volviendo al menú...");
                    break;
                case "4":
                    authService.logout();
                    System.out.println("Sesión cerrada. Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (!selectedOption.equals("4"));
        sc.close();
    }

    public static String[] enterCredentials(Scanner sc) {
        System.out.println("Ingrese correo:");
        String email = sc.nextLine();
        System.out.println("Ingrese contraseña:");
        String password = sc.nextLine();
        return new String[]{email, password};
    }
}