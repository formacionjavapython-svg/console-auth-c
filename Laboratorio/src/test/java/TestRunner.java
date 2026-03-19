package test.java;

import java.util.List;

public class TestRunner {
    public static void main(String[] args) {
        run(new TestCase());
    }

    public static void run(TestCase testCase) {
        testCase.testRegisterSuccess();
        testCase.testLoginSuccess();
        testCase.testLoginFailure();
        testCase.testPolicyRejects();

        printResults(testCase.getResults());
    }

    private static void printResults(List<String> results) {
        System.out.println("=== RESULTADOS DE PRUEBAS AUTOMATIZADAS ===");
        boolean allPassed = true;
        for (String result : results) {
            System.out.println(result);
            if (result.startsWith("FAIL")) {
                allPassed = false;
            }
        }

        if (!allPassed) {
            System.err.println("\nEstado: ALGUNAS PRUEBAS FALLARON");
            System.exit(1);
        } else {
            System.out.println("\nEstado: TODAS LAS PRUEBAS PASARON (OK)");
        }
    }
}

