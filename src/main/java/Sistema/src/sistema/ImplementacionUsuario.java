
package sistema;

import java.util.ArrayList;
import sistema.Rules.PasswordPolicy;
import sistema.Rules.MinLenghtRule;
import sistema.Rules.ContainsNumberRule;
import sistema.Rules.NoEmailInPasswordRule;


public class ImplementacionUsuario implements RepositorioUsuarios {
    private ArrayList<Usuario> usuarios = new ArrayList<>();
    private PasswordPolicy policy;

    public ImplementacionUsuario() {
        policy = new PasswordPolicy();
        policy.addRule(new MinLenghtRule());
        policy.addRule(new ContainsNumberRule());
        policy.addRule(new NoEmailInPasswordRule());
    }

    @Override
    public Usuario guardarUsuario(String email, String password) {
        if (buscarPorEmail(email) != null) {
            throw new IllegalArgumentException("El usuario ya existe");
        }

        Usuario nuevo = new Usuario(
            new Email(email),
            new Password(password, email, policy)
        );

        usuarios.add(nuevo);
        return nuevo;
    }

    @Override
    public Usuario buscarPorEmail(String email) {
        for (Usuario u : usuarios) {
            if (u.getEmail().getValor().equals(email)) {
                return u;
            }
        }
        return null;
    }

}
