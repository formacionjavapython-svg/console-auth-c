package com.consoleauthlab.service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

/**
 * Servicio seguro para operaciones de hash de contraseñas usando PBKDF2.
 * Implementa salt único por contraseña y pepper de entorno.
 */
public class HashService {
    
    // Configuración de PBKDF2
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final int ITERATIONS = 100000; // Número de iteraciones
    private static final int KEY_LENGTH = 256; // Longitud de clave en bits
    private static final int SALT_LENGTH = 32; // Longitud de salt en bytes
    
    // Pepper debería venir de una variable de entorno en producción
    // Por ahora usamos un valor por defecto (EN PRODUCCIÓN CAMBIAR!)
    private static final String DEFAULT_PEPPER = "ChangeThisInProduction!";

    private final String pepper;
    private final SecureRandom secureRandom;

    public HashService() {
        this.pepper = System.getenv("AUTH_PEPPER") != null
                ? System.getenv("AUTH_PEPPER")
                : DEFAULT_PEPPER;
        this.secureRandom = new SecureRandom();
    }

    /**
     * Genera un hash seguro para una contraseña usando PBKDF2.
     * Incluye salt único y pepper.
     */
    public String generateHash(String password) {
        try {
            // Generar salt único
            byte[] salt = generateSalt();

            // Combinar password con pepper
            String passwordWithPepper = password + pepper;

            // Generar hash con PBKDF2
            byte[] hash = pbkdf2(passwordWithPepper.toCharArray(), salt, ITERATIONS, KEY_LENGTH);

            // Formato: algoritmo:iteraciones:salt:hash (en Base64)
            return String.format("pbkdf2:%d:%s:%s",
                    ITERATIONS,
                    Base64.getEncoder().encodeToString(salt),
                    Base64.getEncoder().encodeToString(hash));

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Error generando hash seguro", e);
        }
    }

    /**
     * Verifica si una contraseña coincide con un hash almacenado.
     */
    public boolean verifyHash(String password, String storedHash) {
        try {
            // Parsear el hash almacenado
            String[] parts = storedHash.split(":");
            if (parts.length != 4 || !parts[0].equals("pbkdf2")) {
                return false; // Formato inválido
            }

            int iterations = Integer.parseInt(parts[1]);
            byte[] salt = Base64.getDecoder().decode(parts[2]);
            byte[] expectedHash = Base64.getDecoder().decode(parts[3]);

            // Combinar password con pepper
            String passwordWithPepper = password + pepper;

            // Generar hash con los mismos parámetros
            byte[] testHash = pbkdf2(passwordWithPepper.toCharArray(), salt, iterations, KEY_LENGTH);

            // Comparación segura contra timing attacks
            return constantTimeEquals(expectedHash, testHash);

        } catch (Exception e) {
            return false; // Cualquier error = verificación fallida
        }
    }
    
    /**
     * Valida las políticas de seguridad de la contraseña.
     * Configurable: se podrían hacer parámetros en el futuro.
     */
    public boolean validatePasswordPolicy(String password) {
        if (password == null || password.length() < 12) { // Más largo para mayor seguridad
            return false;
        }
        
        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;
        
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpperCase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowerCase = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecial = true;
            }
        }

        // Política: mínimo 12 caracteres, con mayúscula, minúscula, número y especial
        return hasUpperCase && hasLowerCase && hasDigit && hasSpecial;
    }

    /**
     * Genera un salt criptográficamente seguro.
     */
    private byte[] generateSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        secureRandom.nextBytes(salt);
        return salt;
    }

    /**
     * Implementación de PBKDF2.
     */
    private byte[] pbkdf2(char[] password, byte[] salt, int iterations, int keyLength)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, keyLength);
        SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
        return factory.generateSecret(spec).getEncoded();
    }

    /**
     * Comparación segura contra timing attacks.
     */
    private boolean constantTimeEquals(byte[] a, byte[] b) {
        if (a.length != b.length) {
            return false;
        }

        int result = 0;
        for (int i = 0; i < a.length; i++) {
            result |= a[i] ^ b[i];
        }
        return result == 0;
    }

    /**
     * Obtiene la política de contraseña como texto para mostrar al usuario.
     */
    public String getPasswordPolicyDescription() {
        return "La contraseña debe tener:\n" +
                "• Mínimo 12 caracteres\n" +
                "• Al menos una letra mayúscula\n" +
                "• Al menos una letra minúscula\n" +
                "• Al menos un número\n" +
                "• Al menos un carácter especial (!@#$%^&* etc.)";
    }
}
