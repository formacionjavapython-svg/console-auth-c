package source.util;

public class UtilEmail {

    public static boolean isValid(String email) {
        return email != null && !email.isBlank() && email.contains("@") && email.contains(".");
    }
}
