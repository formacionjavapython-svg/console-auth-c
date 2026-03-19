public class Email {
    private final String value;

    public Email(String value) {
        if (!isValid(value)) {
            throw new IllegalArgumentException("Formato de email inválido");
        }
        this.value = value;
    }

    private boolean isValid(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }

    public String getValue() { return value; }

    @Override
    public String toString() { return value; }
}