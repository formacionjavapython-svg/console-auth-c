package source.test;

import source.model.Email;
import source.model.Password;
import source.model.User;
import source.repository.UserRepositoryInterface;
import source.repository.UserRepositoryLocalImpl;
import source.service.UserService;

import java.util.Optional;

public class TestMain {

    public static void main(String[] args) {

        // Crear repositorio y servicio
        UserRepositoryInterface userRepository = new UserRepositoryLocalImpl();
        UserService userService = new UserService(userRepository);

        // ---- Prueba 1: Registrar usuarios ----
        System.out.println("\n=== Registro de usuarios ===");
        Email email1 = new Email("juan@mail.com");
        Password pass1 = new Password("12345678");
        User user1 = new User(email1, pass1);
        userRepository.addUser(user1);

        Email email2 = new Email("maria@mail.com");
        Password pass2 = new Password("password123");
        User user2 = new User(email2, pass2);
        userRepository.addUser(user2);

        // ---- Prueba 2: Buscar usuarios existentes ----
        System.out.println("\n=== Búsqueda de usuarios ===");
        Optional<User> search1 = userRepository.searchByEmail(new Email("juan@mail.com"));
        search1.ifPresentOrElse(
                u -> System.out.println("Usuario encontrado: " + u.getEmail().getEmail()),
                () -> System.out.println("Usuario no encontrado")
        );

        Optional<User> search2 = userRepository.searchByEmail(new Email("pedro@mail.com"));
        search2.ifPresentOrElse(
                u -> System.out.println("Usuario encontrado: " + u.getEmail().getEmail()),
                () -> System.out.println("Usuario no encontrado")
        );

        // ---- Prueba 3: Login simulado ----
        System.out.println("\n=== Login de usuarios ===");
        loginTest(userRepository, "maria@mail.com", "password123"); // correcto
        loginTest(userRepository, "maria@mail.com", "wrongpass");   // contraseña incorrecta
        loginTest(userRepository, "pedro@mail.com", "12345678");    // usuario no existe
    }

    private static void loginTest(UserRepositoryInterface repo, String emailStr, String passwordStr) {
        Optional<User> userOpt = repo.searchByEmail(new Email(emailStr));
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getPassword().matches(passwordStr)) {
                System.out.println("Login exitoso para: " + emailStr);
            } else {
                System.out.println("Login fallido (contraseña incorrecta) para: " + emailStr);
            }
        } else {
            System.out.println("Login fallido (usuario no encontrado) para: " + emailStr);
        }
    }
}
