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
        // Extraemos el nombre antes del @ y quitamos puntos
        String handle = email.split("@")[0].replace(".", "");
        
        // Si la contraseña contiene alguna parte importante del nombre (ej. "farfa")
        if (password.toLowerCase().contains(handle) || handle.contains(password.toLowerCase())) {
            return "La contraseña no puede contener partes de tu nombre o email.";
        }
        
        // Validación extra por si el usuario usa trozos pequeños
        if (password.length() >= 4 && handle.contains(password.substring(0, 4).toLowerCase())) {
             return "La contraseña es demasiado similar a tu email.";
        }
        
        return null;
    }
}
