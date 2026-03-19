
package sistema;
import java.util.ArrayList;
import java.util.List;

public class Test {
    
    List<String> results = new ArrayList<>();
    ImplementacionUsuario repo = new ImplementacionUsuario(); // tu repositorio de usuarios

    public void testRegisterSuccess() {
        try {
            repo.guardarUsuario("prueba@test.com", "Pass1234");
            results.add("testRegisterSuccess: PASSED");
        } catch (Exception e) {
            results.add("testRegisterSuccess: FAILED - " + e.getMessage());
        }
    }

    public void testLoginSuccess() {
        try {
            Usuario u = repo.buscarPorEmail("prueba@test.com");
            if (u != null && u.getPassword().getValor().equals("Pass1234")) {
                results.add("testLoginSuccess: PASSED");
            } else {
                results.add("testLoginSuccess: FAILED");
            }
        } catch (Exception e) {
            results.add("testLoginSuccess: FAILED - " + e.getMessage());
        }
    }

    public void testLoginFailure() {
        try {
            Usuario u = repo.buscarPorEmail("prueba@test.com");
            if (u != null && !u.getPassword().getValor().equals("Incorrecta")) {
                results.add("testLoginFailure: PASSED");
            } else {
                results.add("testLoginFailure: FAILED");
            }
        } catch (Exception e) {
            results.add("testLoginFailure: FAILED - " + e.getMessage());
        }
    }

    public void testPolicyRejects() {
        try {
            repo.guardarUsuario("prueba2@test.com", "123"); // muy corta, debería fallar
            results.add("testPolicyRejects: FAILED"); // si llega aquí, el test falló
        } catch (Exception e) {
            results.add("testPolicyRejects: PASSED"); // excepción = política de password funciona
        }
    }

    public void printResults() {
        System.out.println("----- Resultados del Test -----");
        for (String r : results) {
            System.out.println(r);
        }
        System.out.println("-------------------------------");
    }
}

