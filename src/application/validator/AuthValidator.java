package application.validator;


public class AuthValidator {

    public static void validateRegister(String email, String password) {

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email requerido");
        }

        if (!email.contains("@")) {
            throw new IllegalArgumentException("Email inválido");
        }

        // 🔐 Delegas a policy
        PasswordPolicy.validate(email, password);
    }
}