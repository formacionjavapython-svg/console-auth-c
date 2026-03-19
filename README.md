# Sistema de Autenticación de Consola (Java)

Este proyecto es un sistema de gestión de usuarios que implementa estándares de seguridad modernos para el registro y autenticación.

## 🚀 Características y Criterios de Aceptación
- **Programación Orientada a Objetos (POO)**: Estructura dividida en modelos (`User`), lógica de negocio (`AuthService`) e interfaz (`Main`).
- **Política de Contraseñas**: Validación de robustez (mínimo 8 caracteres, una mayúscula y un número).
- **Criptografía Avanzada**: Implementación de **PBKDF2 con HMAC-SHA1**.
- **Seguridad contra Ataques**:
    - **Salt Aleatorio**: Cada usuario tiene un salt único de 16 bytes para evitar Rainbow Tables.
    - **Pepper**: Capa extra de seguridad estática en el servidor.
    - **Iteraciones**: 65,536 iteraciones para dificultar ataques de fuerza bruta.

## 🛠️ Cómo Ejecutar
1. **Compilar**:
   ```bash
   javac src/main/java/*.java
   ```
2. **Ejecutar Aplicación**:
   ```bash
   java -cp src/main/java Main
   ```
3. **Ejecutar Pruebas (Tests)**:
   ```bash
   java -cp src/main/java AuthServiceTest
   ```

## ✅ Pruebas Unitarias
El proyecto incluye un archivo `AuthServiceTest.java` que valida automáticamente:
1. Que la política de contraseñas bloquee claves débiles.
2. Que el proceso de Hashing y Verificación funcione correctamente.
