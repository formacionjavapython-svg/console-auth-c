package model;

public class Email {
    private final String value;

    public Email(String value) {
        if (!value.contains("@") || !value.contains(".")) {
            throw new IllegalArgumentException("Invalid email");
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Email && ((Email) o).value.equals(this.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}