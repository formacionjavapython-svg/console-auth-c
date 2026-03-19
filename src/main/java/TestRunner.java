public class TestRunner {

    public static void main(String[] args) {
        testRegisterAndLoginSuccess();
        testLoginWrongPassword();
        testPasswordPolicyRejects();
    }

    public static void testRegisterAndLoginSuccess() {
        AuthService auth = new AuthService();
        auth.register("test@mail.com", "123456");

        boolean result = auth.login("test@mail.com", "123456");

        if (result) {
            System.out.println("✔ testRegisterAndLoginSuccess PASÓ");
        } else {
            System.out.println("❌ testRegisterAndLoginSuccess FALLÓ");
        }
    }

    public static void testLoginWrongPassword() {
        AuthService auth = new AuthService();
        auth.register("test@mail.com", "123456");

        boolean result = auth.login("test@mail.com", "wrong");

        if (!result) {
            System.out.println("✔ testLoginWrongPassword PASÓ");
        } else {
            System.out.println("❌ testLoginWrongPassword FALLÓ");
        }
    }

    public static void testPasswordPolicyRejects() {
        try {
            AuthService auth = new AuthService();
            auth.register("test@mail.com", "123"); // contraseña débil

            System.out.println("❌ testPasswordPolicyRejects FALLÓ");
        } catch (IllegalArgumentException e) {
            System.out.println("✔ testPasswordPolicyRejects PASÓ");
        }
    }
}
