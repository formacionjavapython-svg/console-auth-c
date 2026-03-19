
import source.repository.UserRepositoryInterface;
import source.repository.UserRepositoryLocalImpl;
import source.service.UserService;


import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        UserRepositoryInterface userRepository = new UserRepositoryLocalImpl();
        UserService userService = new UserService(userRepository);
        Scanner scanner = new Scanner(System.in);
        boolean repeatMenu = true;

        while (repeatMenu) {
            System.out.println("\n--- Menú ---");
            System.out.println("1. Registrar");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Seleccione una opción: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> userService.processAddUser();
                case "2" -> userService.processSearch();
                case "3" -> {
                    System.out.println("Saliendo del programa. ¡Adiós!");
                    repeatMenu = false;
                }
                default -> System.out.println("Opción inválida. Intente nuevamente.");
            }
        }

        scanner.close();
    }
}
