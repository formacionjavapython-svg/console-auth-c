import java.util.Scanner;

public class Main {
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
                    System.out.println("Iniciando registro...");
                    
                    break;
                case "2":
                    System.out.println("Iniciando sesión...");
                   
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