import java.util.*;

public class AuthService {
    private final List<User> users = new ArrayList<>();
    private final List<PasswordRule> rules = new ArrayList<>();

    public AuthService() {
        // Agregamos las reglas al diseño composable
        rules.add(new MinLengthRule(8));
        rules.add(new ContainsNumberRule());
        rules.add(new NoEmailInPasswordRule());
    }

    public List<String> validatePassword(String password, String email) {
        // Calidad de Código: Validación Defensiva
        Objects.requireNonNull(password, "La contraseña no puede ser nula");
        Objects.requireNonNull(email, "El email no puede ser nulo");
        
        List<String> errors = new ArrayList<>();
        for (PasswordRule rule : rules) {
            String error = rule.validate(password, email);
            if (error != null) {
                errors.add(error);
            }
        }
        return errors;
    }

    // Calidad de Código: Uso de Optional para evitar retornos null
    public Optional<User> findUser(String email) {
        Objects.requireNonNull(email, "El email de búsqueda no puede ser nulo");
        return users.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    public void register(String email, String password) {
        users.add(new User(email, password));
    }
}
