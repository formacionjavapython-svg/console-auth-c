import java.util.List;

public interface PasswordRule {
    String validate(String password, String email);
}

class MinLengthRule implements PasswordRule {
    public String validate(String password, String email) {
        return password.length() >= 8 ? null : "Mínimo 8 caracteres.";
    }
}

class ContainsNumberRule implements PasswordRule {
    public String validate(String password, String email) {
        return password.matches(".*[0-9].*") ? null : "Debe incluir un número.";
    }
}

class NoEmailInPasswordRule implements PasswordRule {
    public String validate(String password, String email) {
        String userPart = email.split("@")[0];
        return !password.contains(userPart) ? null : "La contraseña no puede contener tu email.";
    }
}
