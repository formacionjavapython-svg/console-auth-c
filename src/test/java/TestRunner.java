
public class TestRunner {
    public static void run(TestCase testCase) {
        System.out.println("Running tests...");
        
        try {
            testCase.testRegisterSuccess();
            System.out.println("✓ testRegisterSuccess passed");
        } catch (Exception e) {
            System.out.println("✗ testRegisterSuccess failed: " + e.getMessage());
        }
        
        try {
            testCase.testLoginSuccess();
            System.out.println("✓ testLoginSuccess passed");
        } catch (Exception e) {
            System.out.println("✗ testLoginSuccess failed: " + e.getMessage());
        }
        
        System.out.println("Tests completed");
    }
}