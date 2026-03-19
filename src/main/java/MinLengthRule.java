public class MinLengthRule implements PasswordRule {
    @Override
    public String validate(String password, Email email) {
        return (password.length() < 8) ? "Mínimo 8 caracteres." : null;
    }
}