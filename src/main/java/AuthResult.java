/**
 * Resultado de una operacion de autenticacion.
 * Patron Result para diseno funcional (sin excepciones para flujo de control).
 */
public final class AuthResult {

    private final boolean success;
    private final String message;

    private AuthResult(final boolean success, final String message) {
        this.success = success;
        this.message = message;
    }

    public static AuthResult ok(final String message) {
        return new AuthResult(true, message);
    }

    public static AuthResult fail(final String message) {
        return new AuthResult(false, message);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
