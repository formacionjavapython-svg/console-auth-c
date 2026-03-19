package test.java;

import main.java.auth.AuthService;

import java.util.ArrayList;
import java.util.List;

public class TestCase {
    private final AuthService auth = new AuthService();
    private final List<String> results = new ArrayList<>();

    public List<String> getResults() {
        return results;
    }

    public void testRegisterSuccess() {
        String res = auth.register("test@test.com", "Password123!");
        results.add(res.contains("éxito") ? "PASS: Registro exitoso" : "FAIL: Registro fallido");
    }

    public void testLoginSuccess() {
        auth.register("login@test.com", "Password123!");
        String res = auth.login("login@test.com", "Password123!");
        results.add(res.contains("Éxito") ? "PASS: Login exitoso" : "FAIL: Login fallido");
    }

    public void testLoginFailure() {
        auth.register("wrong@test.com", "Password123!");
        String res = auth.login("wrong@test.com", "WrongPass123!");
        results.add(res.contains("Error") ? "PASS: Error de login detectado" : "FAIL: El login no falló");
    }

    public void testPolicyRejects() {
        String res = auth.register("bad@test.com", "123");
        results.add(res.contains("política") ? "PASS: Política rechazó clave débil" : "FAIL: Política no funcionó");
    }
}
