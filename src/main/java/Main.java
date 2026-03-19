import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private static UserRepository repository = new InMemoryUserRepository();
    private static PasswordPolicy passwordPolicy = new PasswordPolicy();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n1. Register\n2. Login\n3. Exit");
            System.out.print("Seleccione una opción: ");
            String op = scanner.nextLine();
            
            if (op.equals("3")) break;
            
            if (op.equals("1")) {
                registrar(scanner);
            } else if (op.equals("2")) {
                loguear(scanner);
            }
        }
    }

    private static void registrar(Scanner scanner) {
        try {
            System.out.print("Email: ");
            Email email = new Email(scanner.nextLine());
            System.out.print("Password: ");
            String pass = scanner.nextLine();

            List<String> errores = passwordPolicy.validate(pass, email);
            if (errores.isEmpty()) {
                String hashedPass = SecurityUtils.hashPassword(pass);
                repository.save(new User(email, hashedPass));
                System.out.println("¡Registrado con éxito!");
            } else {
                System.out.println("Errores de validación:");
                errores.forEach(err -> System.out.println("- " + err));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void loguear(Scanner scanner) {
        System.out.print("Email: ");
        String emailInput = scanner.nextLine();
        System.out.print("Password: ");
        String passInput = scanner.nextLine();

        Optional<User> userOpt = repository.findByEmail(emailInput);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // Verificamos si la contraseña coincide con el hash guardado
            if (SecurityUtils.verifyPassword(passInput, user.getPasswordHash())) {
                System.out.println("¡Login exitoso! Bienvenido " + emailInput);
            } else {
                System.out.println("Error: Contraseña incorrecta.");
            }
        } else {
            System.out.println("Error: El usuario no existe.");
        }
    }
}