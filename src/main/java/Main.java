import rules.Email;
import rules.PasswordPolicy;
import rules.MinLengthRule;
import rules.ContainsNumberRule;
import rules.NoEmailInPasswordRule;
import java.util.Scanner;
import java.util.Optional;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        
        UserRepository repository = new InMemoryUserRepository();
        
        
        PasswordPolicy policy = new PasswordPolicy();
        policy.addRule(new MinLengthRule(8));            
        policy.addRule(new ContainsNumberRule());        
        policy.addRule(new NoEmailInPasswordRule());     

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("==========================================");
        System.out.println("   BIENVENIDO AL LABORATORIO DE AUTENTICACIÓN");
        System.out.println("==========================================");

        while (running) {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("1. Register (Registrarse)");
            System.out.println("2. Login (Iniciar sesión)");
            System.out.println("3. Exit (Salir)");
            System.out.print("Seleccione una opción: ");
            
            String option = scanner.nextLine();

            switch (option) {
                case "1": 
                    System.out.println("\n[REGISTRO NUEVO USUARIO]");
                    System.out.print("Email: ");
                    String emailReg = scanner.nextLine();
                    System.out.print("Password: ");
                    String passReg = scanner.nextLine();

                    try {
                        Email email = new Email(emailReg);
                        
                        List<String> errors = policy.validate(passReg, email);
                        
                        if (errors.isEmpty()) {
                            
                            User newUser = new User(email, passReg);
                            repository.save(newUser);
                        } else {
                            
                            System.out.println("\nERROR DE SEGURIDAD:");
                            for (String error : errors) {
                                System.out.println("   - " + error);
                            }
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("ERROR: " + e.getMessage());
                    }
                    break;

                case "2": 
                    System.out.println("\n[INICIO DE SESION]");
                    System.out.print("Email: ");
                    String emailLoginStr = scanner.nextLine();
                    System.out.print("Password: ");
                    String passLogin = scanner.nextLine();

                    try {
                        Email emailLogin = new Email(emailLoginStr);
                        Optional<User> userFound = repository.findByEmail(emailLogin);

                        if (userFound.isPresent() && userFound.get().getPasswordHash().equals(passLogin)) {
                            System.out.println("\nBienvenido al sistema");
                        } else {
                            System.out.println("\nERROR: email o contraseña incorrectos");
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("ERROR: Formato de correo invalido");
                    }
                    break;

                case "3":
                    running = false;
                    System.out.println("\nCierre de sesion");
                    break;

                default:
                    System.out.println("\nOpcion invalida por favor intenta de nuevo");
            }
        }
        scanner.close();
    }
}