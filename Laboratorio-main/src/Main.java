import model.*;
import repository.*;
import security.*;

import java.util.Scanner;

public class Main {

    private static final UserRepository repo = new InMemoryUserRepository();
    private static final PasswordPolicy policy = new PasswordPolicy();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Register\n2. Login\n3. Exit");
            String option = sc.nextLine();

            switch (option) {
                case "1":
                    register(sc);
                    break;
                case "2":
                    login(sc);
                    break;
                case "3":
                    System.out.println("Bye!");
                    return;
                default:
                    System.out.println("Invalid option");
            }
        }
    }

    private static void register(Scanner sc) {
        try {
            System.out.print("Email: ");
            Email email = new Email(sc.nextLine());

            System.out.print("Password: ");
            String password = sc.nextLine();

            var errors = policy.validate(password, email);
            if (!errors.isEmpty()) {
                errors.forEach(System.out::println);
                return;
            }

            PasswordHash hash = PasswordUtils.hash(password);
            repo.save(new User(email, hash));

            System.out.println("User registered!");
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    private static void login(Scanner sc) {
        System.out.print("Email: ");
        String emailInput = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        var userOpt = repo.findByEmail(new Email(emailInput));

        if (userOpt.isPresent()) {
            boolean ok = userOpt.get().getHash().matches(password);
            System.out.println(ok ? "Login success" : "Invalid credentials");
        } else {
            System.out.println("Invalid credentials");
        }
    }
}