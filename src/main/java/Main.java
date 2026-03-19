import java.util.Scanner;
import java.util.List;

public class Main {
    
    private static AuthService authService = new AuthService();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("--- Bienvenido ---");

        while (running) {
            System.out.println( "\nSeleccione una opción");
            System.out.println("\n1. Register\n2. Login\n3. Exit");
            System.out.print("> ");
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    registrarUsuario(scanner);
                    break;
                case "2":
                    iniciarSesion(scanner);
                    break;
                case "3":
                    System.out.println("Saliendo...");
                    running = false;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
        scanner.close();
    }

    
    private static void registrarUsuario(Scanner sc) {
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Password: ");
        String pass = sc.nextLine();

        
        List<String> errores = authService.validatePassword(pass, email);
        if (errores.isEmpty()) {
            authService.register(email, pass);
            System.out.println("¡Registro exitoso!");
        } else {
            errores.forEach(System.out::println);
        }
    }

    private static void iniciarSesion(Scanner sc) {
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Password: ");
        String pass = sc.nextLine();

       
        if (authService.login(email, pass)) {
            System.out.println("¡Bienvenido!");
        } else {
            System.out.println("Error: Credenciales inválidas.");
        }
    }
}