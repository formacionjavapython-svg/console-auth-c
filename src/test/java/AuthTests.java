package test.java;

import java.util.LinkedList;
import main.java.service.AuthService;

public class AuthTests {
    private AuthService authService = new AuthService();

    public void testRegisterAndLoginSuccess() {
        authService.logout();
        authService.register("correo@gmail.com", "manticora69");
        authService.login("correo@gmail.com", "manticora69");
        if (authService.getCurrentUser() == null) {
            System.out.println("FAILURE: Falló al registrar o loguear al usuario");
        } else {
            System.out.println("SUCCESS: Registro y login exitosos");
        }
    }

    public void testLoginWrongPasswordFailure() {
        authService.logout();
        authService.register("correo@gmail.com", "manticora69");
        LinkedList<String> status = authService.login("correo@gmail.com", "manticOra69899");
        if (status.size() > 0) { 
            System.out.println("SUCCESS: Login falló exitosamente por contraseña incorrecta");
            for (String error : status) {
                System.out.println("Violaciones a la política de contraseña: " + error);
            }
        } else {
            System.out.println("FAILURE: Login exitoso, pero se esperaba fallo por contraseña incorrecta");
        }
    }

    public void testRegisterPasswordPolicyRejects() {
        authService.logout();
        LinkedList<String> status = authService.register("correo@gmail.com", "manti");
        if (status.size() > 0) {
            System.out.println("SUCCESS: Registro falló exitosamente por política de contraseña");
            for (String error : status) {
                System.out.println("Violaciones a la política de contraseña: " + error);
            }
        } else {
            System.out.println("FAILURE: Registro exitoso, pero se esperaba fallo por política de contraseña");
        }
    }    
}
