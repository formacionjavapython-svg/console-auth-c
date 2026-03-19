import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AuthService authService = new AuthService(new InMemoryUserRepository());
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("1. Registrar usuario");
            System.out.println("2. Iniciar sesión");
            System.out.println("3. Salir");
            System.out.print("Elige una opción:\n");

            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    System.out.print("Email: ");
                    String emailReg = scanner.nextLine();
                    System.out.print("Password: ");
                    String passReg = scanner.nextLine();
                    if (authService.register(emailReg, passReg)) {
                        System.out.println("Usuario registrado con éxito!");
                    } else {
                        System.out.println("El usuario ya existe.");
                    }
                    break;

                case "2":
                    System.out.print("Email: ");
                    String emailLog = scanner.nextLine();
                    System.out.print("Password: ");
                    String passLog = scanner.nextLine();
                    if (authService.login(emailLog, passLog)) {
                        System.out.println("Login exitoso!");
                    } else {
                        System.out.println("Credenciales incorrectas.");
                    }
                    break;

                case "3":
                    running = false;
                    break;

                default:
                    System.out.println("Opción no válida.");
            }
        }

        scanner.close();
    }
}