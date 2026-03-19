public class TestRunner {

    public static void run() {

        TestCases test = new TestCases();

        test.testRegisterAndLoginSuccess();
        test.testLoginWrongPassword();
        test.testPasswordPolicyRejects();

    }
}
