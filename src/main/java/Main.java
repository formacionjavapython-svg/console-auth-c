import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AuthService auth = new AuthService();

        System.out.println("--- REGISTRO DE USUARIO (PASO 2) ---");
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Contraseña: ");
        String pass = sc.nextLine();

        if (auth.validatePasswordPolicy(pass)) {
            try {
                byte[] salt = auth.generateSalt();
                String hash = auth.hashPassword(pass, salt);
                System.out.println("\n[EXITO] Usuario registrado.");
                System.out.println("Tu contraseña en la DB se vería así: " + hash);
            } catch (Exception e) {
                System.out.println("Error en el proceso de seguridad.");
            }
        } else {
            System.out.println("\n[ERROR] La contraseña debe tener mín. 8 caracteres, una mayúscula y un número.");
        }
    }
}
