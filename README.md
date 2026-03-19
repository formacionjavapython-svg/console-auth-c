# Sistema de Registro y Login en Java
# C36 Sandoval Mandujano Samuel

Laboratorio Practico — Sistema completo de autenticacion en consola.
Java 17 | Sin frameworks | Almacenamiento en memoria.

# Estructura

```
src/main/java/
├── Main.java                  → Interfaz de consola (punto de entrada)
├── User.java                  → Modelo (POO)
├── UserRepository.java        → Repositorio en memoria
├── AuthService.java           → Logica de negocio
├── AuthResult.java            → Patron Result funcional
├── HashingService.java        → PBKDF2 + HMAC-SHA1 + salt + pepper
├── EmailValidator.java        → Validacion de email
├── PasswordRule.java          → Interfaz de regla composable
├── PasswordPolicy.java        → Composicion de reglas
├── MinLengthRule.java         → Regla: longitud minima
├── ContainsUppercaseRule.java → Regla: al menos una mayuscula
├── ContainsNumberRule.java    → Regla: al menos un numero
├── NoEmailInPasswordRule.java → Regla: no incluir email
└── AuthServiceTest.java       → Pruebas automatizadas (TestRunner custom)
```

# Ejecutar

```bash
# Compilar
javac src/main/java/*.java

# Ejecutar aplicacion
java -cp src/main/java Main

# Ejecutar pruebas (tests)
java -cp src/main/java AuthServiceTest
```

# Seguridad

- **Hashing**: PBKDF2WithHmacSHA1 (65,536 iteraciones, Java estandar).
- **Salt**: Unico por usuario (16 bytes, SecureRandom) — evita Rainbow Tables.
- **Pepper**: Variable de entorno `AUTH_PEPPER` — capa extra en el servidor.
- **Mensajes genericos**: No se filtra si un email existe o no.
- **Timing-attack**: Comparacion en tiempo constante + hash ficticio en login fallido.
- **Sin sesiones activas**: Al salir, todo es elegible para garbage collector.

# Diseno Funcional: Password Policy Composable

Validacion de passwords usando composicion de reglas. Cada regla es independiente
y puede combinarse con otras, permitiendo extensibilidad sin modificar codigo existente
(principio Open/Closed).

- `PasswordRule` — Interfaz: metodo `validate(password, email)` retorna violaciones.
- `MinLengthRule` — Longitud minima configurable (ej: 8 caracteres).
- `ContainsUppercaseRule` — Al menos una mayuscula.
- `ContainsNumberRule` — Al menos un digito.
- `NoEmailInPasswordRule` — Previene email en la contrasena.
- `PasswordPolicy` — Compone reglas y ejecuta `validate()` agregando todas las violaciones.

# CI/CD

GitHub Actions ejecuta automaticamente: build → test en cada push.
