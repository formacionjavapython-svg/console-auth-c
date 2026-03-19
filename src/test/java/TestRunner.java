import java.util.ArrayList;
import java.util.List;

public class TestRunner {
  public static void main(String[] args) {
    System.out.println("=======================================");
    System.out.println("   EJECUTANDO PRUEBAS AUTOMATIZADAS    ");
    System.out.println("=======================================");
    
    AuthTest testCase = new AuthTest();
    List<String> results = new ArrayList<>();

    // Ejecución de los casos
    results.add(testCase.testRegisterSuccess());
    results.add(testCase.testLoginFailure());
    results.add(testCase.testPolicyRejects());

    // Mostrar resultados
    for (String resultado : results) {
      System.out.println(resultado);
    }
    
    System.out.println("=======================================");
  }
}
