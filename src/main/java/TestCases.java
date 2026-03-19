public class TestCases {

    public void testRegisterAndLoginSuccess() {

        AuthService auth = new AuthService();

        auth.register("test@mail.com", "123456");

        boolean result = auth.login("test@mail.com", "123456");

        if (result) {
            System.out.println("✔ testRegisterAndLoginSuccess PASÓ");
        } else {
            System.out.println("❌ testRegisterAndLoginSuccess FALLÓ");
        }
    }
    public void testLoginWrongPassword() {
        AuthService auth = new AuthService();

        auth.register("test@mail.com", "123456");

        boolean result = auth.login("test@mail.com", "000000");

        if (!result) {
            System.out.println("✔ testLoginWrongPassword PASÓ");
        } else {
            System.out.println("❌ testLoginWrongPassword FALLÓ");
        }
    }

    public void testPasswordPolicyRejects() {
        PasswordPolicy policy = new PasswordPolicy();
        policy.addRule(new MinLengthRule());
        policy.addRule(new HasNumberRule());

        var errors = policy.validate("abc");

        if (!errors.isEmpty()) {
            System.out.println("✔ testPasswordPolicyRejects PASÓ");
        } else {
            System.out.println("❌ testPasswordPolicyRejects FALLÓ");
        }
    }
}
