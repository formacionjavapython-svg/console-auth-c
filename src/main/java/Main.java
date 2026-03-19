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
                        // Guardamos hash y salt juntos usando un separador ":"
                        String savedData = hash + ":" + Base64.getEncoder().encodeToString(salt);
                        database.add(new User(email, savedData));
                        System.out.println("[OK] Usuario registrado correctamente.");
                    } catch (Exception e) { System.out.println("[!] Error de seguridad."); }
                } else { System.out.println("[!] Password muy débil."); }

            } else if (op == 2) {
                System.out.print("Email: ");
                String email = sc.nextLine();
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
                                System.out.println("\n[ERROR] Contraseña incorrecta.");
                            }
                            encontrado = true;
                        } catch (Exception e) { System.out.println("[!] Error al validar."); }
                    }
                }
                if (!encontrado) System.out.println("\n[ERROR] El usuario no existe.");
            } else if (op == 3) {
                System.out.println("Saliendo del sistema...");
                break;
            }
        }
    }
}
