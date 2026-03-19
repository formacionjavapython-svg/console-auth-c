public class ContainsNumberRule implements PasswordRule {
    @Override
    public String validate(String password, Email email) {
        return (!password.matches(".*\\d.*")) ? "Debe contener al menos un número." : null;
    }
}