package util;

import java.security.MessageDigest;

public class PasswordUtil {

    // Se hace con md5 por motivos de practicidad
    // Generar hash MD5
    public static String hash(String password) {
        System.out.println(password);
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(password.getBytes());

            return bytesToHex(digest);

        } catch (Exception e) {
            throw new RuntimeException("Error al generar hash", e);
        }
    }

    // Verificar password
    public static boolean verify(String password, String storedHash) {
        String hashedInput = hash(password);
        return hashedInput.equals(storedHash);
    }

    // Convertir a HEX (correcto)
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hex = new StringBuilder();

        for (byte b : bytes) {
            String s = Integer.toHexString(0xff & b);
            if (s.length() == 1) hex.append('0');
            hex.append(s);
        }

        return hex.toString();
    }
}