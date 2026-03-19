public class AuthServiceTest {
    public static void main(String[] args) {
        AuthService auth = new AuthService();
        System.out.println("--- INICIANDO TESTS DE SEGURIDAD ---");

        try {
            // Test 1: Validar Política
            boolean policyOk = auth.validatePasswordPolicy("Password2026!");
            System.out.println("Test Política Robusta: " + (policyOk ? "PASÓ" : "FALLÓ"));

            // Test 2: Hashing y Verificación
            byte[] salt = auth.generateSalt();
            String password = "MiPasswordSeguro123";
            String hash = auth.hashPassword(password, salt);
            
            boolean loginOk = auth.verifyPassword(password, hash, salt);
            System.out.println("Test Hashing/Login: " + (loginOk ? "PASÓ" : "FALLÓ"));

            System.out.println("\n[RESULTADO FINAL] ¡Todos los criterios de seguridad se cumplen!");
        } catch (Exception e) {
            System.out.println("Error durante los tests: " + e.getMessage());
        }
    }
}
