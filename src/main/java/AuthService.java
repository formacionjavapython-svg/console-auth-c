import java.util.HashMap;

public class AuthService {

    private HashMap<String, User> users = new HashMap<>();

    public void register(String email, String password) {
        User user = new User(email, password);
        users.put(email, user);
        System.out.println("Usuario registrado");
    }

    public boolean login(String email, String password) {
        User user = users.get(email);

        if (user == null) {
            return false;
        }

        return user.getPasswordHash().equals(password);
    }
}