import java.util.List;

public class TestRunner {
    private static UserRepository repo;
    private static PasswordPolicy policy;
    private static int failedTests = 0;

    public static void main(String[] args) {
        System.out.println("=== CI/CD: EJECUTANDO PRUEBAS AUTOMATIZADAS ===");
        
        try {
            runTest("Registro + Login Exitoso", TestRunner::testRegisterAndLoginSuccess);
            runTest("Login con Password Incorrecto", TestRunner::testLoginPasswordIncorrect);
            runTest("Password Policy Bloquea Débiles", TestRunner::testPasswordPolicyRejectsWeak);
            
            if (failedTests > 0) {
                System.err.println("\n CI/CD FALLIDO: " + failedTests + " pruebas fallaron.");
                System.exit(1); // Esto detiene el Workflow de GitHub si hay errores
            } else {
                System.out.println("\n CI/CD EXITOSO: Todas las pruebas pasaron.");
                System.exit(0);
            }
        } catch (Exception e) {
            System.err.println("\n ERROR EN EL RUNNER: " + e.getMessage());
            System.exit(1);
        }
    }

    // Método auxiliar para manejar las pruebas 
    private static void runTest(String name, Runnable test) {
        try {
            setup();
            test.run();
            System.out.println("PASÓ: " + name);
        } catch (Throwable e) {
            System.err.println("FALLÓ: " + name + " -> " + e.getMessage());
            failedTests++;
        }
    }

    private static void setup() {
        repo = new InMemoryUserRepository();
        policy = new PasswordPolicy();
    }

    private static void testRegisterAndLoginSuccess() {
        Email email = new Email("test@pro.com");
        String pass = "Secure1234";
        repo.save(new User(email, SecurityUtils.hashPassword(pass)));
        
        User saved = repo.findByEmail("test@pro.com").orElseThrow();
        if (!SecurityUtils.verifyPassword(pass, saved.getPasswordHash())) {
            throw new RuntimeException("El login debió ser exitoso");
        }
    }

    private static void testLoginPasswordIncorrect() {
        Email email = new Email("hacker@web.com");
        repo.save(new User(email, SecurityUtils.hashPassword("ClaveReal123")));
        
        User saved = repo.findByEmail("hacker@web.com").orElseThrow();
        if (SecurityUtils.verifyPassword("ClaveFalsa", saved.getPasswordHash())) {
            throw new RuntimeException("El login debió fallar con clave falsa");
        }
    }

    private static void testPasswordPolicyRejectsWeak() {
        Email email = new Email("user@test.com");
        List<String> violations = policy.validate("123", email);
        if (violations.isEmpty()) {
            throw new RuntimeException("La política debió rechazar el password '123'");
        }
    }
}