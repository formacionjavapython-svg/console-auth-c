public class NoEmailInPasswordRule implements PasswordRule {
    @Override
    public String validate(String password, Email email) {
        String userPart = email.getValue().split("@")[0];
        return (password.contains(userPart)) ? "No puede contener partes del email." : null;
    }
}