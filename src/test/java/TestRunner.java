package test.java;

import java.util.ArrayList;
import java.util.List;

public class TestRunner {
    
    public static void run(AuthTests testCase) {
        List<String> results = new ArrayList<>();

        // ejecución d los tests
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
        boolean anyFailed = false;
        System.out.println("-- RESULTADOS DE TESTS ---");
        for (String res : results) {
            System.out.println(res);
            if (res.contains("FAILURE")) {
                anyFailed = true;
            }
        }

        if (anyFailed) {
            System.err.println("Se detectaron fallos en las pruebas.");
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        run(new AuthTests());
    }
}
