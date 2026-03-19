package main;

import application.service.AuthService;
import application.service.IAuthService;
import domain.model.IUserRepository;
import infrastructure.repository.InMemoryUserRepository;
import presentation.controller.AuthController;
import presentation.view.ConsoleView;
import util.InputCustom;

public class Main {
    public static void main(String[] args) {

        IUserRepository repository = new InMemoryUserRepository();
        IAuthService authService = new AuthService(repository);
        AuthController controller = new AuthController(authService);
        ConsoleView view = new ConsoleView();

        boolean running = true;

        while (running) {
            view.showMenu();

            String option = InputCustom.readLine("");

            switch (option) {
                case "1":
                    String user = InputCustom.readLine("Usuario: ");
                    String pass = InputCustom.readLine("Password: ");
                    controller.login(user, pass);
                    break;

                case "2":
                    String newUser = InputCustom.readLine("Nuevo usuario: ");
                    String newPass = InputCustom.readLine("Password: ");
                    controller.register(newUser, newPass);
                    break;

                case "3":
                    running = false;
                    System.out.println("Saliendo...");
                    repository.clearAll();
                    break;

                default:
                    System.out.println("Opción inválida");
            }
        }
    }
}