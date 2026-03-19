import java.util.ArrayList;
import java.util.List;

public class TestRunner {
    
    public static void main(String[] args) {
        System.out.println("--- Ejecutando Pruebas Automatizadas ---");
        
        // Lista para guardar los resultados de cada test
        List<String> results = new ArrayList<>();

        // Ejecución de los casos obligatorios
        results.add(testRegisterSuccess());
        results.add(testLoginFailure());
        results.add(testPolicyRejects());

        // Imprimir resultados finales
        System.out.println("\n--- Resumen de Pruebas ---");
        results.forEach(System.out::println);
    }

    // Caso 1: Registro y Login Exitoso
    static String testRegisterSuccess() {
        AuthService auth = new AuthService(); // Preparar
        String email = "admin@test.com";
        String pass = "Admin2026!";
        
        auth.register(email, pass); // Ejecutar
        
        // Verificar usando el método de login que ya creaste
        boolean success = auth.login(email, pass); 
        return "Test Register + Login: " + (success ? "PASÓ " : "FALLÓ");
    }

    // Caso 2: Login con contraseña incorrecta
    static String testLoginFailure() {
        AuthService auth = new AuthService();
        auth.register("user@test.com", "User12345!");
        
        // Intentar con clave errónea
        boolean success = auth.login("user@test.com", "ClaveIncorrecta"); 
        return "Test Login Incorrecto: " + (!success ? "PASÓ" : "FALLÓ");
    }

    // Caso 3: Password Policy bloquea claves débiles
    static String testPolicyRejects() {
        AuthService auth = new AuthService();
        // Intentamos clave corta sin números ni mayúsculas
        List<String> errors = auth.validatePassword("123", "bad@test.com");
        
        return "Test Policy Bloquea: " + (!errors.isEmpty() ? "PASÓ " : "FALLÓ");
    }
}