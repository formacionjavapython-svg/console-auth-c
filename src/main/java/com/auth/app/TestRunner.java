package com.auth.app;

import java.util.ArrayList;
import java.util.List;

public class TestRunner {
    public static void run(AuthServiceTest testCase) {

    List<String> results = new ArrayList<>();

    results.add(testCase.testRegisterSuccess());
    results.add(testCase.testLoginSuccess());
    results.add(testCase.testLoginFailure());
    results.add(testCase.testPolicyRejects());

    printResults(results);
  }

  private static void printResults(List<String> results) {

    System.out.println("\n--- RESULTADOS DE PRUEBAS ---");

    int passed = 0;

    for (String result : results) {
      System.out.println(result);
      if (result.contains("PASÓ")) {
        passed++;
      }
    }

    System.out.println("\nResumen: " + passed + "/" + results.size() + " PASARON");
  }    
}
