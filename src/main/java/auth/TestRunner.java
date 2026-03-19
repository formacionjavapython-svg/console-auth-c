package auth;

public class TestRunner {

    public static void main(String[] args) {

        AuthServiceTest test = new AuthServiceTest();

        test.testRegisterSuccess();
        test.testLoginSuccess();
        test.testLoginFail();
        test.testPasswordPolicy();

        System.out.println("=== TODOS LOS TESTS EJECUTADOS ===");
    }
}