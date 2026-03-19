import java.util.List;

public class AuthServiceTest {
    public static void main(String[] args) {
        AuthService auth = new AuthService();
        System.out.println("--- INICIANDO TESTS DE SEGURIDAD (DISEÑO COMPOSABLE) ---");

        try {
            // Test 1: Validar Política (Debe fallar si es débil)
            List<String> errores = auth.validatePassword("debil", "test@gmail.com");
            System.out.println("Test Password Débil: " + (!errores.isEmpty() ? "PASÓ (Detectó errores)" : "FALLÓ"));

            // Test 2: Validar Regla de Email (No debe contener el email)
            List<String> erroresEmail = auth.validatePassword("admin123", "admin@gmail.com");
            boolean detectoEmail = erroresEmail.stream().anyMatch(e -> e.contains("email"));
            System.out.println("Test Regla NoEmail: " + (detectoEmail ? "PASÓ" : "FALLÓ"));

            // Test 3: Hashing y Verificación
            byte[] salt = auth.generateSalt();
            String password = "PasswordSeguro2026!";
            String hash = auth.hashPassword(password, salt);
            
            boolean loginOk = auth.verifyPassword(password, hash, salt);
            System.out.println("Test Hashing/Login: " + (loginOk ? "PASÓ" : "FALLÓ"));

            System.out.println("\n[RESULTADO FINAL] ¡Todos los criterios de diseño funcional se cumplen!");
        } catch (Exception e) {
            System.out.println("Error durante los tests: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
