public class AuthService {
    private UserRepository repo = new UserRepository();

    public void register(String email, String password) {
        if (repo.findByEmail(email) != null) {
            System.out.println("Usuario ya existe");
            return;
        }
        repo.save(new User(email, password));
        System.out.println("Registro exitoso");
    }

    public void login(String email, String password) {
        User user = repo.findByEmail(email);

        if (user == null || !user.password.equals(password)) {
            System.out.println("Credenciales incorrectas");
        } else {
            System.out.println("Login exitoso");
        }
    }
}