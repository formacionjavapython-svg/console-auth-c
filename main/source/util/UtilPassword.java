package source.util;

public class UtilPassword {

    public static boolean isValid(String password) {
        return password != null && !password.isBlank() && password.length() >= 8;
    }
}
