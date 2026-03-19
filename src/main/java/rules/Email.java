package rules;

import java.util.Objects;

public class Email {
  private final String value;

  public Email(String value) {
    this.value = Objects.requireNonNull(value, "el valor del email no puede ser nulo");
    
    if (!isValid(value)) {
      throw new IllegalArgumentException("formato de email invalido");
    }
  }

  private boolean isValid(String email) {
    if (email == null) {
      return false;
    }
    
    if (email.contains("@") && email.contains(".")) {
      return true;
    }
    
    return false;
  }

  public String getValue() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Email email = (Email) o;
    return value.equals(email.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}