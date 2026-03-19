
package sistema;
import java.util.Scanner;


public class Sistema {


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ImplementacionUsuario repo = new ImplementacionUsuario();

        Usuario usuarioActual = null;
        

        while (true) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Registrar");
            System.out.println("2. Login");
            System.out.println("3. Logout");
            System.out.println("4. Salir");

            int opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (opcion) {

                case 1:
                    System.out.print("Email: ");
                    String email = sc.nextLine();

                    System.out.print("Password: ");
                    String password = sc.nextLine();

                    try {
                        repo.guardarUsuario(email, password);
                        System.out.println("Usuario registrado");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        
                    }
                    break;

                case 2:
                    System.out.print("Email: ");
                    email = sc.nextLine();

                    System.out.print("Password: ");
                    password = sc.nextLine();

                    Usuario u = repo.buscarPorEmail(email);

                    if (u != null && u.getPassword().getValor().equals(password)) {
                        usuarioActual = u;
                        System.out.println("Login exitoso");
                    } else {
                        System.out.println("Credenciales incorrectas");
                    }
                    break;

                case 3:
                    usuarioActual = null;
                    System.out.println("Sesion cerrada");
                    break;

                case 4:
                    System.out.println("Saliendo...");
                    return;

                default:
                    System.out.println("Opción inválida");
            }
        }
        
    }
    
}
