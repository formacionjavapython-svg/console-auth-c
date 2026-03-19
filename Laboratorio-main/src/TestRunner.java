import java.util.ArrayList;
import java.util.List;
import model.*;
import repository.*;
import security.*;

public class TestRunner {

    public static void main(String[] args) {
        List<String> results = new ArrayList<>();

        runTest("Register + Login Success", TestRunner::testRegisterLoginSuccess, results);
        runTest("Login Wrong Password", TestRunner::testLoginWrongPassword, results);
        runTest("Password Policy Rejects", TestRunner::testPasswordPolicyRejects, results);

        System.out.println("\n=== RESULTS ===");
        results.forEach(System.out::println);
    }

    private static void runTest(String name, Runnable test, List<String> results) {
        try {
            test.run();
            results.add("✓ " + name);
        } catch (AssertionError e) {
            results.add("X " + name + " -> " + e.getMessage());
        } catch (Exception e) {
            results.add("X " + name + " -> Exception: " + e.getMessage());
        }
    }

    // =========================
    // TESTS
    // =========================

    // 1. Register + Login exitoso
    private static void testRegisterLoginSuccess() {
        UserRepository repo = new InMemoryUserRepository();
        PasswordPolicy policy = new PasswordPolicy();

        Email email = new Email("test@mail.com");
        String password = "Password1";

        if (!policy.validate(password, email).isEmpty()) {
            throw new AssertionError("Password should be valid");
        }

        PasswordHash hash = PasswordUtils.hash(password);
        repo.save(new User(email, hash));

        User user = repo.findByEmail(email)
                .orElseThrow(() -> new AssertionError("User not found"));

        if (!user.getHash().matches(password)) {
            throw new AssertionError("Login should succeed");
        }
    }

    // 2. Login con password incorrecto
    private static void testLoginWrongPassword() {
        UserRepository repo = new InMemoryUserRepository();

        Email email = new Email("test@mail.com");
        String password = "Password1";

        PasswordHash hash = PasswordUtils.hash(password);
        repo.save(new User(email, hash));

        User user = repo.findByEmail(email)
                .orElseThrow(() -> new AssertionError("User not found"));

        if (user.getHash().matches("WrongPass1")) {
            throw new AssertionError("Login should fail");
        }
    }

    // 3. Password policy bloquea
    private static void testPasswordPolicyRejects() {
        PasswordPolicy policy = new PasswordPolicy();

        Email email = new Email("test@mail.com");
        String weakPassword = "123";

        if (policy.validate(weakPassword, email).isEmpty()) {
            throw new AssertionError("Weak password should be rejected");
        }
    }
}