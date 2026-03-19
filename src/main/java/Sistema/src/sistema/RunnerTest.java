
package sistema;

public class RunnerTest {
      public static void main(String[] args) {
        Test tests = new Test();

        tests.testRegisterSuccess();
        tests.testLoginSuccess();
        tests.testLoginFailure();
        tests.testPolicyRejects();

        tests.printResults();
    }
}
