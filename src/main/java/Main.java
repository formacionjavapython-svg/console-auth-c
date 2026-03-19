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
            System.out.println("\n--- SISTEMA DE AUTENTICACIÓN ---");
            System.out.println("1. Registrarse");
            System.out.println("2. Iniciar Sesión");
            System.out.println("3. Salir");
            System.out.print("Opción: ");
            int op = sc.nextInt();
            sc.nextLine(); 

            if (op == 1) {
                System.out.print("Email: ");
                String email = sc.nextLine();
                System.out.print("Password: ");
                String pass = sc.nextLine();
                if (auth.validatePasswordPolicy(pass)) {
                    try {
                        byte[] salt = auth.generateSalt();
                        String hash = auth.hashPassword(pass, salt);
                        database.add(new User(email, hash + ":" + Base64.getEncoder().encodeToString(salt)));
                        System.out.println("[OK] Registrado.");
                    } catch (Exception e) { System.out.println("Error."); }
                } else { System.out.println("[!] Password débil."); }

            } else if (op == 2) {
                System.out.print("Email: ");
                String email = sc.nextLine();
                System.out.print("Password: ");
                String pass = sc.nextLine();
                
                boolean found = false;
                for (User u : database) {
                    if (u.getEmail().equals(email)) {
                        try {
                            String[] parts = u.getPasswordHash().split(":");
                            byte[] salt = Base64.getDecoder().decode(parts[1]);
                            if (auth.verifyPassword(pass, parts[0], salt)) {
                                System.out.println("[BIENVENIDO] Acceso concedido.");
                            } else {
                                System.out.println("[ERROR] Credenciales inválidas.");
                            }
                            found = true;
                        } catch (Exception e) { System.out.println("Error."); }
                    }
                }
                if (!found) System.out.println("[ERROR] Usuario no existe.");
            } else if (op == 3) break;
        }
    }
}
