import java.util.regex.Pattern;
import java.util.regex.Matcher;
public class ValidaEmail {
    private static final String PASSWORD_PATTERN ="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";
    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
public static boolean validarPassword (String password)
{
    if (password == null) {
        return false;
    }
    Matcher matcher = pattern.matcher(password);
    return matcher.matches();
}
}
