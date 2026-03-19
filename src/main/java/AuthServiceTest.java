import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Pruebas automatizadas sin JUnit (TestRunner custom).
 * Valida automaticamente:
 * 1. Que la politica de contrasenas bloquee claves debiles.
 * 2. Que el proceso de Hashing y Verificacion funcione correctamente.
 * 3. Que el registro y login funcionen end-to-end.
 * 4. Que los mensajes de error sean genericos (sin filtrar informacion).
 * 5. Que las reglas composables funcionen independientemente.
 *
 * Ejecutar: java -cp src/main/java AuthServiceTest
 */
public final class AuthServiceTest {

    private static int passed = 0;
    private static int failed = 0;
    private static final List<String> failures = new ArrayList<>();

    // ==================== Assertions ====================

    private static void assertTrue(final boolean condition, final String msg) {
        if (!condition) {
            throw new RuntimeException("assertTrue fallo: " + msg);
        }
    }

    private static void assertFalse(final boolean condition, final String msg) {
        if (condition) {
            throw new RuntimeException("assertFalse fallo: " + msg);
        }
    }

    private static void assertEquals(final Object expected, final Object actual, final String msg) {
        if (expected == null && actual == null) {
            return;
        }
        if (expected == null || !expected.equals(actual)) {
            throw new RuntimeException("assertEquals fallo: " + msg
                    + " | esperado: " + expected + " | actual: " + actual);
        }
    }

    private static void assertNotNull(final Object obj, final String msg) {
        if (obj == null) {
            throw new RuntimeException("assertNotNull fallo: " + msg);
        }
    }

    // ==================== Helper ====================

    private static AuthService createService() {
        return new AuthService(
                new UserRepository(),
                new HashingService("testPepper"),
                PasswordPolicy.defaultPolicy()
        );
    }

    // ==================== Tests: Password Policy ====================

    public void testPolicyRejectsShortPassword() {
        final PasswordPolicy policy = PasswordPolicy.defaultPolicy();
        final List<String> v = policy.validate("Ab1xyzz", "user@test.com");
        assertFalse(v.isEmpty(), "Password corta (7 chars) debe fallar");
    }

    public void testPolicyRejectsNoUppercase() {
        final PasswordPolicy policy = PasswordPolicy.defaultPolicy();
        final List<String> v = policy.validate("abcdefg1", "user@test.com");
        assertFalse(v.isEmpty(), "Sin mayuscula debe fallar");
    }

    public void testPolicyRejectsNoNumber() {
        final PasswordPolicy policy = PasswordPolicy.defaultPolicy();
        final List<String> v = policy.validate("Abcdefgh", "user@test.com");
        assertFalse(v.isEmpty(), "Sin numero debe fallar");
    }

    public void testPolicyRejectsEmailInPassword() {
        final PasswordPolicy policy = PasswordPolicy.defaultPolicy();
        final List<String> v = policy.validate("juan123A!", "juan@test.com");
        assertFalse(v.isEmpty(), "Password con parte del email debe fallar");
    }

    public void testPolicyAcceptsValidPassword() {
        final PasswordPolicy policy = PasswordPolicy.defaultPolicy();
        final List<String> v = policy.validate("Secure1pass", "user@test.com");
        assertTrue(v.isEmpty(), "Password valida no debe tener violaciones");
    }

    public void testPolicyRejectsNull() {
        final PasswordPolicy policy = PasswordPolicy.defaultPolicy();
        final List<String> v = policy.validate(null, "user@test.com");
        assertFalse(v.isEmpty(), "Password null debe fallar");
    }

    // ==================== Tests: Composable Rules ====================

    public void testMinLengthRuleIndependently() {
        final MinLengthRule rule = new MinLengthRule(10);
        assertFalse(rule.validate("short", "a@b.com").isEmpty(), "MinLength 10 rechaza 'short'");
        assertTrue(rule.validate("1234567890", "a@b.com").isEmpty(), "MinLength 10 acepta 10 chars");
    }

    public void testContainsNumberRuleIndependently() {
        final ContainsNumberRule rule = new ContainsNumberRule();
        assertFalse(rule.validate("NoDigitsHere", "a@b.com").isEmpty(), "Rechaza sin digitos");
        assertTrue(rule.validate("Has1Digit", "a@b.com").isEmpty(), "Acepta con digito");
    }

    public void testNoEmailInPasswordRuleIndependently() {
        final NoEmailInPasswordRule rule = new NoEmailInPasswordRule();
        assertFalse(rule.validate("mycarlos123", "carlos@mail.com").isEmpty(),
                "Rechaza password con parte del email");
        assertTrue(rule.validate("Secure1pass", "carlos@mail.com").isEmpty(),
                "Acepta password sin partes del email");
    }

    public void testContainsUppercaseRuleIndependently() {
        final ContainsUppercaseRule rule = new ContainsUppercaseRule();
        assertFalse(rule.validate("nouppercase1", "a@b.com").isEmpty(), "Rechaza sin mayuscula");
        assertTrue(rule.validate("HasUpper1", "a@b.com").isEmpty(), "Acepta con mayuscula");
    }

    // ==================== Tests: Hashing ====================

    public void testHashGeneratesSalt() {
        final HashingService hs = new HashingService("pepper");
        final String salt = hs.generateSalt();
        assertNotNull(salt, "Salt no debe ser null");
        assertFalse(salt.isEmpty(), "Salt no debe estar vacio");
    }

    public void testHashUniqueSalts() {
        final HashingService hs = new HashingService("pepper");
        final String s1 = hs.generateSalt();
        final String s2 = hs.generateSalt();
        assertFalse(s1.equals(s2), "Cada salt debe ser unico");
    }

    public void testHashDeterministic() {
        final HashingService hs = new HashingService("pepper");
        final String salt = hs.generateSalt();
        final String h1 = hs.hash("password", salt);
        final String h2 = hs.hash("password", salt);
        assertEquals(h1, h2, "Mismo input debe dar mismo hash");
    }

    public void testHashVerifyCorrect() {
        final HashingService hs = new HashingService("pepper");
        final String salt = hs.generateSalt();
        final String hash = hs.hash("MyPass1!", salt);
        assertTrue(hs.verify("MyPass1!", salt, hash), "Password correcta debe verificar");
    }

    public void testHashVerifyWrong() {
        final HashingService hs = new HashingService("pepper");
        final String salt = hs.generateSalt();
        final String hash = hs.hash("MyPass1!", salt);
        assertFalse(hs.verify("WrongPass1!", salt, hash), "Password incorrecta no debe verificar");
    }

    public void testHashDifferentSaltDifferentHash() {
        final HashingService hs = new HashingService("pepper");
        final String s1 = hs.generateSalt();
        final String s2 = hs.generateSalt();
        assertFalse(hs.hash("pass", s1).equals(hs.hash("pass", s2)),
                "Diferente salt debe dar diferente hash");
    }

    public void testHashDifferentPepperDifferentHash() {
        final HashingService hs1 = new HashingService("pepper1");
        final HashingService hs2 = new HashingService("pepper2");
        final String salt = hs1.generateSalt();
        assertFalse(hs1.hash("pass", salt).equals(hs2.hash("pass", salt)),
                "Diferente pepper debe dar diferente hash");
    }

    // ==================== Tests: AuthService (Register) ====================

    public void testRegisterSuccess() {
        final AuthService svc = createService();
        final AuthResult r = svc.register("user@example.com", "Secure1pass");
        assertTrue(r.isSuccess(), "Registro valido debe ser exitoso");
    }

    public void testRegisterInvalidEmail() {
        final AuthService svc = createService();
        final AuthResult r = svc.register("not-an-email", "Secure1pass");
        assertFalse(r.isSuccess(), "Email invalido debe fallar");
    }

    public void testRegisterWeakPassword() {
        final AuthService svc = createService();
        final AuthResult r = svc.register("user@example.com", "weak");
        assertFalse(r.isSuccess(), "Password debil debe fallar");
    }

    public void testRegisterDuplicateGenericMessage() {
        final AuthService svc = createService();
        svc.register("user@example.com", "Secure1pass");
        final AuthResult r = svc.register("user@example.com", "Secure1pass");
        assertFalse(r.isSuccess(), "Duplicado debe fallar");
        assertEquals("Credenciales invalidas.", r.getMessage(),
                "Debe dar mensaje generico, no revelar que email ya existe");
    }

    // ==================== Tests: AuthService (Login) ====================

    public void testLoginSuccess() {
        final AuthService svc = createService();
        svc.register("login@example.com", "Secure1pass");
        final AuthResult r = svc.login("login@example.com", "Secure1pass");
        assertTrue(r.isSuccess(), "Login correcto debe ser exitoso");
    }

    public void testLoginWrongPasswordGenericMessage() {
        final AuthService svc = createService();
        svc.register("user@example.com", "Secure1pass");
        final AuthResult r = svc.login("user@example.com", "WrongPass1");
        assertFalse(r.isSuccess(), "Password incorrecta debe fallar");
        assertEquals("Credenciales invalidas.", r.getMessage(),
                "Login fallido debe dar mensaje generico");
    }

    public void testLoginNonExistentGenericMessage() {
        final AuthService svc = createService();
        final AuthResult r = svc.login("ghost@example.com", "Secure1pass");
        assertFalse(r.isSuccess(), "Usuario inexistente debe fallar");
        assertEquals("Credenciales invalidas.", r.getMessage(),
                "Usuario inexistente debe dar MISMO mensaje generico");
    }

    public void testLoginEmptyFields() {
        final AuthService svc = createService();
        assertFalse(svc.login("", "Secure1pass").isSuccess(), "Email vacio debe fallar");
        assertFalse(svc.login("user@example.com", "").isSuccess(), "Password vacia debe fallar");
    }

    public void testLoginCaseInsensitiveEmail() {
        final AuthService svc = createService();
        svc.register("User@Example.COM", "Secure1pass");
        final AuthResult r = svc.login("user@example.com", "Secure1pass");
        assertTrue(r.isSuccess(), "Email debe ser case-insensitive");
    }

    // ==================== Tests: Email Validator ====================

    public void testEmailValidatorAcceptsValid() {
        assertTrue(EmailValidator.isValid("user@example.com"), "Email estandar debe ser valido");
    }

    public void testEmailValidatorRejectsInvalid() {
        assertFalse(EmailValidator.isValid("not-email"), "Sin @ debe ser invalido");
        assertFalse(EmailValidator.isValid(null), "Null debe ser invalido");
        assertFalse(EmailValidator.isValid(""), "Vacio debe ser invalido");
        assertFalse(EmailValidator.isValid("user@"), "Sin dominio debe ser invalido");
    }

    // ==================== Test Runner ====================

    public static void main(final String[] args) {
        System.out.println("========================================");
        System.out.println("  EJECUTANDO PRUEBAS AUTOMATIZADAS");
        System.out.println("========================================\n");

        final AuthServiceTest instance = new AuthServiceTest();

        for (final Method method : AuthServiceTest.class.getDeclaredMethods()) {
            if (method.getName().startsWith("test") && method.getParameterCount() == 0) {
                try {
                    method.invoke(instance);
                    System.out.println("  [PASS] " + method.getName());
                    passed++;
                } catch (Exception e) {
                    final String cause = (e.getCause() != null)
                            ? e.getCause().getMessage() : e.getMessage();
                    System.out.println("  [FAIL] " + method.getName() + " -> " + cause);
                    failed++;
                    failures.add(method.getName() + ": " + cause);
                }
            }
        }

        System.out.println("\n========================================");
        System.out.println("  RESUMEN DE PRUEBAS");
        System.out.println("========================================");
        System.out.println("  Total:    " + (passed + failed));
        System.out.println("  Pasadas:  " + passed);
        System.out.println("  Fallidas: " + failed);
        System.out.println("========================================");

        if (!failures.isEmpty()) {
            System.out.println("\n  Detalles de fallos:");
            for (final String f : failures) {
                System.out.println("    - " + f);
            }
        }

        System.out.println();
        if (failed > 0) {
            System.out.println("RESULTADO: FALLIDO");
            System.exit(1);
        } else {
            System.out.println("RESULTADO: EXITOSO");
        }
    }
}
