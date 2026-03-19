import java.util.List;
import java.util.Optional;

public class AuthServiceTest {
    public static void main(String[] args) {
        System.out.println("--- INICIANDO TEST RUNNER PERSONALIZADO ---");
        testRegisterAndLoginSuccess();
        testPasswordPolicyBlocks();
        System.out.println("--- TODAS LAS PRUEBAS FINALIZADAS ---");
    }

    static void testRegisterAndLoginSuccess() {
        AuthService auth = new AuthService();
        auth.register("irvin@test.com", "Password2026!");
        
        // Verificamos usando isPresent() del Optional
        Optional<User> user = auth.findUser("irvin@test.com");
        System.out.println("Test Registro y Búsqueda (Optional): " + (user.isPresent() ? "PASÓ" : "FALLÓ"));
    }

    static void testPasswordPolicyBlocks() {
        AuthService auth = new AuthService();
        List<String> errors = auth.validatePassword("123", "irvin@test.com");
        System.out.println("Test Política Bloquea (Composabilidad): " + (!errors.isEmpty() ? "PASÓ" : "FALLÓ"));
    }
}
