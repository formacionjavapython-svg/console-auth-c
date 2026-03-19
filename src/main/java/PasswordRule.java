public interface PasswordRule {
    String validate(String password, Email email);
}