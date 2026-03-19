C38-Tania-Mendez
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

import java.util.Scanner;
import java.util.Base64;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static List<User> database = new ArrayList<>();
    private static AuthService auth = new AuthService();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n--- SISTEMA DE AUTENTICACIÓN (DISEÑO FUNCIONAL) ---");
            System.out.println("1. Registrarse");
            System.out.println("2. Iniciar Sesión");
            System.out.println("3. Salir");
            System.out.print("Opción: ");
            String input = sc.nextLine();
            
            if (input.equals("1")) {
                System.out.print("Email: ");
                String email = sc.nextLine().trim().toLowerCase();
                System.out.print("Password: ");
                String pass = sc.nextLine();
                
                // Usamos el nuevo validador composable
                List<String> errores = auth.validatePassword(pass, email);
                
                if (errores.isEmpty()) {
                    try {
                        byte[] salt = auth.generateSalt();
                        String hash = auth.hashPassword(pass, salt);
                        String savedData = hash + ":" + Base64.getEncoder().encodeToString(salt);
                        database.add(new User(email, savedData));
                        System.out.println("[OK] Usuario registrado con éxito.");
                    } catch (Exception e) { System.out.println("[!] Error interno."); }
                } else {
                    System.out.println("[ERROR] La contraseña es débil:");
                    for (String err : errores) {
                        System.out.println(" - " + err);
                    }
                }

            } else if (input.equals("2")) {
                System.out.print("Email: ");
                String email = sc.nextLine().trim().toLowerCase();
                System.out.print("Password: ");
                String pass = sc.nextLine();
                
                boolean encontrado = false;
                for (User u : database) {
                    if (u.getEmail().equals(email)) {
                        try {
                            String[] parts = u.getPasswordHash().split(":");
                            byte[] salt = Base64.getDecoder().decode(parts[1]);
                            if (auth.verifyPassword(pass, parts[0], salt)) {
                                System.out.println("\n[BIENVENIDO] ¡Acceso concedido!");
                            } else {
                                System.out.println("\n[ERROR] Credenciales inválidas.");
                            }
                            encontrado = true;
                            break;
                        } catch (Exception e) { System.out.println("[!] Error al validar."); }
                    }
                }
                if (!encontrado) System.out.println("\n[ERROR] Credenciales inválidas.");
            } else if (input.equals("3")) {
                System.out.println("Cerrando sistema...");
                break;
            }
        }
    }
}
main
