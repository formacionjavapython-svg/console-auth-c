package main.java.auth;

import main.java.config.Auth;

import java.util.HashMap;
import java.util.Map;

public class AuthService {
    private final Map<String, user> repository = new HashMap<>();

    public String register(String email, String password) {
        if (!password.matches(Auth.PASSWORD_POLICY)) {
            return "Error: La contraseña no cumple con la política de seguridad.";
        }
        if (repository.containsKey(email)) {
            return "Error: El registro no pudo ser procesado."; // Genérico
        }

        try {
            byte[] salt = PasswordPolicy.generateSalt();
            String hash = PasswordPolicy.hash(password, salt, Auth.PEPPER);
            repository.put(email, new user(email, hash, salt));
            return "Usuario registrado con éxito.";
        } catch (Exception e) {
            return "Error interno del sistema.";
        }
    }

    public String login(String email, String password) {
        user user = repository.get(email);
        try {
            if (user != null) {
                String attemptHash = PasswordPolicy.hash(password, user.getSalt(), Auth.PEPPER);
                if (attemptHash.equals(user.getPasswordHash())) {
                    return "Éxito: Sesión iniciada para " + email;
                }
            }
        } catch (Exception e) { /* Loguear error de forma segura */ }

        return "Error: Credenciales no válidas."; // Genérico para evitar enumeración
    }
}
