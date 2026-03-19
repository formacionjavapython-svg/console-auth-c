import java.util.Objects;

public interface PasswordRule {
    String validate(String password, String email);
}

class MinLengthRule implements PasswordRule {
    private final int min;
    public MinLengthRule(int min) { this.min = min; }
    public String validate(String password, String email) {
        return password.length() >= min ? null : "Mínimo " + min + " caracteres.";
    }
}

class ContainsNumberRule implements PasswordRule {
    public String validate(String password, String email) {
        return password.matches(".*[0-9].*") ? null : "Debe incluir un número.";
    }
}

class NoEmailInPasswordRule implements PasswordRule {
    public String validate(String password, String email) {
        String handle = email.split("@")[0].replace(".", "").toLowerCase();
        if (password.toLowerCase().contains(handle)) {
            return "La contraseña no puede contener tu email.";
        }
        return null;
    }
}
