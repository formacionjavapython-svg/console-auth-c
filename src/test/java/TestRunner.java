package test.java;

import java.util.ArrayList;
import java.util.List;

public class TestRunner {
    
    public static void run(AuthTests testCase) {
        List<String> results = new ArrayList<>();

        // Ejecutamos cada test y guardamos el resultado
        results.add(executeTest("testRegisterAndLoginSuccess", () -> testCase.testRegisterAndLoginSuccess()));
        results.add(executeTest("testLoginWrongPasswordFailure", () -> testCase.testLoginWrongPasswordFailure()));
        results.add(executeTest("testRegisterPasswordPolicyRejects", () -> testCase.testRegisterPasswordPolicyRejects()));

        printResults(results);
    }

    private static String executeTest(String name, Runnable testMethod) {
        try {
            testMethod.run();
            return name + ": SUCCESS - PASSED";
        } catch (Exception e) {
            return name + ": FAILURE (" + e.getMessage() + ")";
        }
    }

    private static void printResults(List<String> results) {
        System.out.println("-- RESULTADOS DE TESTS ---");
        for (String res : results) {
            System.out.println(res);
        }
    }

    public static void main(String[] args) {
        run(new AuthTests());
    }
}
