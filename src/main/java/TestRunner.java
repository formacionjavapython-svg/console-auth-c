import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * TestRunner custom sin JUnit ni frameworks.
 * Ejecuta todas las pruebas de AuthServiceTest y genera reporte.
 *
 * Ejecutar: java -cp out TestRunner
 */
public final class TestRunner {

    private static int passed = 0;
    private static int failed = 0;
    private static final List<String> failures = new ArrayList<>();

    public static void main(final String[] args) {
        System.out.println("========================================");
        System.out.println("  EJECUTANDO PRUEBAS AUTOMATIZADAS");
        System.out.println("========================================\n");

        runTestClass(AuthServiceTest.class);

        System.out.println("\n========================================");
        System.out.println("  RESUMEN DE PRUEBAS");
        System.out.println("========================================");
        System.out.println("  Total:    " + (passed + failed));
        System.out.println("  Pasadas:  " + passed);
        System.out.println("  Fallidas: " + failed);
        System.out.println("========================================");

        if (!failures.isEmpty()) {
            System.out.println("\n  Detalles de fallos:");
            for (final String f : failures) {
                System.out.println("    - " + f);
            }
        }

        System.out.println();
        if (failed > 0) {
            System.out.println("RESULTADO: FALLIDO");
            System.exit(1);
        } else {
            System.out.println("RESULTADO: EXITOSO");
        }
    }

    private static void runTestClass(final Class<?> testClass) {
        System.out.println(">> " + testClass.getSimpleName());
        try {
            final Object instance = testClass.getDeclaredConstructor().newInstance();
            for (final Method method : testClass.getDeclaredMethods()) {
                if (method.getName().startsWith("test") && method.getParameterCount() == 0) {
                    try {
                        method.invoke(instance);
                        System.out.println("  [PASS] " + method.getName());
                        passed++;
                    } catch (Exception e) {
                        final String cause = (e.getCause() != null)
                                ? e.getCause().getMessage() : e.getMessage();
                        System.out.println("  [FAIL] " + method.getName() + " -> " + cause);
                        failed++;
                        failures.add(method.getName() + ": " + cause);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("  [FAIL] No se pudo instanciar: " + e.getMessage());
            failed++;
        }
    }
}
