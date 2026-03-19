public class HasNumberRule implements PasswordRule {

    @Override
    public String validate(String password) {
        if (!password.matches(".*\\d.*")) {
            return "Debe tener al menos un número";
        }
        return null;
    }
}