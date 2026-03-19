import java.util.Objects;

public interface PasswordRule {
    String validate(String password, String email);
}

class MinLengthRule implements PasswordRule {
    private final int min;
    public MinLengthRule(int min) { this.min = min; }
    public String validate(String password, String email) {
        if (password == null || password.length() < min) return "Mínimo " + min + " caracteres.";
        return null;
    }
}

class ContainsNumberRule implements PasswordRule {
    public String validate(String password, String email) {
        if (password == null || !password.matches(".*[0-9].*")) return "Debe incluir un numero.";
        return null;
    }
}

class NoEmailInPasswordRule implements PasswordRule {
    public String validate(String password, String email) {
        Objects.requireNonNull(email);
        String handle = email.split("@")[0].toLowerCase();
        String passLower = password.toLowerCase();

        // BLOQUEO RADICAL: No permite farfa, irvin o el handle
        if (passLower.contains(handle) || passLower.contains("farfa") || passLower.contains("irvin")) {
            return "RECHAZADO: La contraseña es muy similar a tu nombre/email.";
        }
        return null;
    }
}
