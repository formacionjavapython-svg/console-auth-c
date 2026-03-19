package source.service;

import source.model.Email;
import source.model.Password;
import source.model.User;
import source.repository.UserRepositoryInterface;
import source.util.InputService;
import source.util.UtilEmail;
import source.util.UtilPassword;

public class UserService {

    private UserRepositoryInterface userRepository;
    private InputService input = new InputService();

    public UserService(UserRepositoryInterface userRepository) {
        this.userRepository = userRepository;
    }

    public boolean processAddUser() {
        String emailScann = input.inputText("Ingrese el email:");
        if (!UtilEmail.isValid(emailScann)) {
            System.out.println("El email no cumple con las condiciones");
            return false;
        }

        String passwordScann = input.inputText("Ingrese el password (mínimo 8 caracteres):");
        if (!UtilPassword.isValid(passwordScann)) {
            System.out.println("El password no cumple con las condiciones");
            return false;
        }

        Email emailUser = new Email(emailScann);
        Password passwordUser = new Password(passwordScann);
        User user = new User(emailUser, passwordUser);

        userRepository.addUser(user);
        return true;
    }

    public boolean processSearch() {
        String emailScann = input.inputText("Ingrese el email:");
        if (!UtilEmail.isValid(emailScann)) {
            System.out.println("El email no cumple con las condiciones");
            return false;
        }

        Email userEmail = new Email(emailScann);

        userRepository.searchByEmail(userEmail).ifPresentOrElse(user -> {
            String passwordInput = input.inputText("Ingrese el password:");
            if (user.getPassword().matches(passwordInput)) {
                System.out.println("Login exitoso");
            } else {
                System.out.println("Password incorrecto");
            }
        }, () -> {
            System.out.println("No se encontró el usuario con ese email");
        });

        return true;
    }
}
