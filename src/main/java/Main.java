import java.io.Console;
import java.util.Scanner;

/**
 * Aplicacion de consola interactiva (Interfaz).
 * Menu: Registrarse, Iniciar Sesion, Salir.
 *
 * No mantiene sesiones activas: al cerrar se descarta todo (GC).
 * El pepper se lee de variable de entorno AUTH_PEPPER.
 */
public final class Main {

    private static final String MENU = """

            ===================================
              Sistema de Autenticacion
            ===================================
              1. Registrarse
              2. Iniciar Sesion
              3. Salir
            ===================================
            Seleccione una opcion:\s""";

    private final AuthService authService;
    private final Scanner scanner;
    private boolean running;

    public Main(final AuthService authService, final Scanner scanner) {
        this.authService = authService;
        this.scanner = scanner;
        this.running = true;
    }

    /**
     * Bucle principal de la aplicacion.
     */
    public void run() {
        while (running) {
            System.out.print(MENU);
            final String option = scanner.nextLine().trim();

            switch (option) {
                case "1" -> handleRegister();
                case "2" -> handleLogin();
                case "3" -> handleExit();
                default -> System.out.println("[!] Opcion no valida. Intente de nuevo.");
            }
        }
    }

    private void handleRegister() {
        System.out.println("\n--- Registro ---");
        System.out.print("Email: ");
        final String email = scanner.nextLine().trim();

        final String password = readPassword("Contrasena: ");

        final AuthResult result = authService.register(email, password);
        printResult(result);
    }

    private void handleLogin() {
        System.out.println("\n--- Iniciar Sesion ---");
        System.out.print("Email: ");
        final String email = scanner.nextLine().trim();

        final String password = readPassword("Contrasena: ");

        final AuthResult result = authService.login(email, password);
        printResult(result);

        // No se mantiene sesion activa: el resultado es efimero.
        // Variables locales son elegibles para GC al salir del metodo.
    }

    private void handleExit() {
        System.out.println("\nSaliendo del sistema. Hasta luego.");
        running = false;
        // Al terminar, todas las referencias en memoria son elegibles para GC.
    }

    /**
     * Lee la contrasena desde consola (oculta si es posible).
     * Si no hay consola (IDE), usa Scanner.
     */
    private String readPassword(final String prompt) {
        final Console console = System.console();
        if (console != null) {
            final char[] pwd = console.readPassword(prompt);
            return new String(pwd);
        }
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private void printResult(final AuthResult result) {
        final String prefix = result.isSuccess() ? "[OK] " : "[ERROR] ";
        System.out.println(prefix + result.getMessage());
    }

    // --- Entry point ---

    public static void main(final String[] args) {
        // Pepper desde variable de entorno (configurable)
        final String pepper = System.getenv().getOrDefault("AUTH_PEPPER", "defaultPepper2025!");

        final var repository = new UserRepository();
        final var hashingService = new HashingService(pepper);
        final var passwordPolicy = PasswordPolicy.defaultPolicy();
        final var authService = new AuthService(repository, hashingService, passwordPolicy);

        try (var inputScanner = new Scanner(System.in)) {
            final var app = new Main(authService, inputScanner);
            app.run();
        }

        // Al llegar aqui, todas las referencias se liberan -> GC
        System.out.println("Recursos liberados. Programa terminado.");
    }
}
