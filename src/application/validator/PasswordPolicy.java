package application.validator;


public class PasswordPolicy {

    public static void validate(String email, String password) {

        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("El password debe tener al menos 8 caracteres");
        }

        if (email != null && email.contains("@")) {

            String usernamePart = email.split("@")[0];

            if (password.toLowerCase().contains(usernamePart.toLowerCase())) {
                throw new IllegalArgumentException("El password no debe contener el nombre de usuario del correo");
            }
        }
    }
}