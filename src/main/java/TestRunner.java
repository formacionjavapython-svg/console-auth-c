import java.util.Collections;
import java.util.List;

public class TestRunner {
    private static final String PEPPER = "secret-pepper";

    public static void main(String[] args) {
        runAllTests();
    }

    public static void runAllTests() {
        testRegisterLoginSuccess();
        testLoginFailure();
        testPasswordPolicyRejects();
    }

    private static void testRegisterLoginSuccess() {
        System.out.print("Test 1: Register & Login Exitoso... ");
        try {

            InMemoryUserRepository repo = new InMemoryUserRepository();
            PBKDF2Service hashing = new PBKDF2Service();

            Email email = new Email("carlos@example.com");
            byte[] salt = hashing.generateSalt();
            byte[] hash = hashing.computeHash("Password123", PEPPER, salt);
            repo.save(new User(email, new PasswordHash(hash, salt)));

            User user = repo.findByEmail(email).orElseThrow();
            boolean success = user.getPasswordHash().matches("Password123", PEPPER, hashing);

            if (success) System.out.println("PASSED");
            else System.out.println("FAILED");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private static void testLoginFailure() {
        System.out.print("Test 2: Login con Password Incorrecto... ");
        try {
            PBKDF2Service hashing = new PBKDF2Service();
            Email email = new Email("test@fail.com");
            byte[] salt = hashing.generateSalt();
            byte[] hash = hashing.computeHash("CorrectPass1", PEPPER, salt);
            PasswordHash storedHash = new PasswordHash(hash, salt);

            boolean success = storedHash.matches("WrongPass2", PEPPER, hashing);

            if (!success) System.out.println("PASSED");
            else System.out.println("FAILED");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private static void testPasswordPolicyRejects() {
        System.out.print("Test 3: Password Policy Bloquea... ");
        PasswordPolicy policy = new PasswordPolicy(List.of(
                new MinLengthRule(8),
                new ContainsNumberRule()
        ));

        List<String> violations = policy.validate("abc", new Email("a@b.com"));

        if (!violations.isEmpty()) System.out.println("PASSED (Detectó " + violations.size() + " errores)");
        else System.out.println("FAILED");
    }
}