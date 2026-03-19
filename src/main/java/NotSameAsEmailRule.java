public class NotSameAsEmailRule implements PasswordRule {

    private String email;

    public NotSameAsEmailRule(String email) {
        this.email = email;
    }

    @Override
    public String validate(String password) {
        if (password.equals(email)) {
            return "El password no puede ser igual al email";
        }
        return null;
    }
}