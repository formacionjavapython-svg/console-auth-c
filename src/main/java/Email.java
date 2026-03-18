public class Email {
    private final String value;

    public Email(String value) {
        if (!isValid(value)) {
            throw new IllegalArgumentException("Email inválido");
        }
        this.value = value;
    }

    private boolean isValid(String value) {
        return value != null && value.contains("@") && value.contains("."); //Evaluación de email o parametros que debe cumplir el correo
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
