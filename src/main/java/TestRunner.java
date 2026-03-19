import java.util.List;

public class TestRunner {
    private static UserRepository repo;
    private static PasswordPolicy policy;

    public static void main(String[] args) {
        System.out.println("=== INICIANDO PRUEBAS AUTOMATIZADAS ===");
        
        try {
            testRegisterAndLoginSuccess();
            testLoginPasswordIncorrect();
            testPasswordPolicyRejectsWeak();
            
            System.out.println("\n ¡TODAS LAS PRUEBAS PASARON EXITOSAMENTE!");
        } catch (Exception e) {
            System.err.println("\n ERROR EN LAS PRUEBAS: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void setup() {
        repo = new InMemoryUserRepository();
        policy = new PasswordPolicy();
    }

    private static void testRegisterAndLoginSuccess() {
        setup();
        System.out.print("Prueba 1: Registro + Login Exitoso... ");
        Email email = new Email("test@pro.com");
        String pass = "Secure1234";
        
        // Registro
        String hashed = SecurityUtils.hashPassword(pass);
        repo.save(new User(email, hashed));
        
        // Login
        User saved = repo.findByEmail("test@pro.com").orElseThrow();
        assert SecurityUtils.verifyPassword(pass, saved.getPasswordHash()) : "El login debió ser exitoso";
        System.out.println("PASÓ");
    }

    private static void testLoginPasswordIncorrect() {
        setup();
        System.out.print("Prueba 2: Login con Password Incorrecto... ");
        Email email = new Email("hacker@web.com");
        repo.save(new User(email, SecurityUtils.hashPassword("ClaveReal123")));
        
        User saved = repo.findByEmail("hacker@web.com").orElseThrow();
        assert !SecurityUtils.verifyPassword("ClaveFalsa", saved.getPasswordHash()) : "El login debió fallar";
        System.out.println("PASÓ");
    }

    private static void testPasswordPolicyRejectsWeak() {
        setup();
        System.out.print("Prueba 3: Password Policy Bloquea Débiles... ");
        Email email = new Email("user@test.com");
        
        List<String> violations = policy.validate("123", email);
        assert !violations.isEmpty() : "La política debió encontrar errores para '123'";
        System.out.println("PASÓ");
    }
}