# 🧑‍💻 Java Console Auth App

Aplicación de consola en Java que implementa un flujo básico de:

* Registro de usuarios
* Login
* Validación de credenciales
* Política de contraseñas

---

## 🏗️ Arquitectura

El proyecto sigue una estructura inspirada en **Clean Architecture**:

```
src/
├── domain/            # Entidades y contratos (User, IUserRepository)
├── application/       # DTOs, servicios, validaciones
├── infrastructure/    # Implementaciones (repositorio en memoria)
├── presentation/      # Controladores y vista consola
└── util/              # Utilidades (PasswordUtil)
```

---

## 🔐 Funcionalidades

* Registro de usuario
* Login con validación
* Password policy:

  * Mínimo 8 caracteres
  * No debe contener el correo
* Hash de contraseña (MD5 / configurable)
* Manejo de errores controlado

---

## 🧪 Tests

El proyecto incluye pruebas sin frameworks externos:

* Uso de `assert`
* Fake repository para aislar lógica
* Cobertura de:

  * Login exitoso
  * Usuario no encontrado
  * Password incorrecto
  * Registro
  * Validaciones de password

---

## ⚙️ Ejecución

### 1. Compilar

```bash
javac -d out $(find src -name "*.java")
```

### 2. Ejecutar aplicación

```bash
java -cp out main.Main
```

### 3. Ejecutar tests

```bash
java -ea -cp out application.service.AuthServiceTest
```

---

## 🚀 CI (GitHub Actions)

El proyecto incluye integración continua:

* Compilación automática
* Ejecución de tests en cada push
* Validación en Pull Requests

Archivo:

```
.github/workflows/ci.yml
```

---

## 🧠 T
