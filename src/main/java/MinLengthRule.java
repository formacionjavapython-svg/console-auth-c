public class MinLengthRule implements PasswordRule {

    @Override
    public String validate(String password) {
        if (password.length() < 6) {
            return "Debe tener al menos 6 caracteres";
        }
        return null;
    }
}