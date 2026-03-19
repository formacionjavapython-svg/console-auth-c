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
            System.out.println("\n--- -SISTEMA DE AUTENTICACIÓN (DISEÑO FUNCIONAL) ---");
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
