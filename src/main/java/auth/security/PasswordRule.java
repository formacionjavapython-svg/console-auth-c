package auth.security;

import java.util.List;

public interface PasswordRule {
    List<String> validate(String password, String email);
}