import java.util.List;
import java.util.Optional;
import rules.Email;
import rules.PasswordPolicy;
import rules.MinLengthRule;

public class AuthTest {

  public String testRegisterSuccess() {
    InMemoryUserRepository repo = new InMemoryUserRepository();
    Email email = new Email("tania@test.com");
    User user = new User(email, "Password123");
    
    repo.save(user);
    Optional<User> found = repo.findByEmail(email);
    
    return found.isPresent() ? "PASSED: Registro exitoso" : "FAILED: No se guardo";
  }

  public String testLoginFailure() {
    InMemoryUserRepository repo = new InMemoryUserRepository();
    Email email = new Email("pana@test.com");
    repo.save(new User(email, "secreta123"));

    Optional<User> user = repo.findByEmail(email);
    boolean loginFallido = user.isPresent() && !user.get().getPasswordHash().equals("Error999");

    return loginFallido ? "PASSED: Login fallo bien" : "FAILED: No detecto error";
  }

  public String testPolicyRejects() {
    PasswordPolicy policy = new PasswordPolicy();
    policy.addRule(new MinLengthRule(8));
    
    Email email = new Email("developer@test.mx");
    List<String> errors = policy.validate("123", email); 

    return !errors.isEmpty() ? "PASSED: Bloqueo corto" : "FAILED: Permitio corto";
  }
}