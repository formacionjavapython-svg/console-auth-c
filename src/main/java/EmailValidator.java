import java.util.regex.Pattern;

/**
 * Validador de formato de email.
 * Usa expresion regular basica (sin dependencias externas).
 */
public final class EmailValidator {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

    private EmailValidator() {
        // Utility class
    }

    public static boolean isValid(final String email) {
        if (email == null || email.isBlank()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }
}
