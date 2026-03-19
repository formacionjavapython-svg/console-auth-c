
package sistema;

interface RepositorioUsuarios {
    Usuario guardarUsuario(String email, String password);
    Usuario buscarPorEmail(String email);
}
