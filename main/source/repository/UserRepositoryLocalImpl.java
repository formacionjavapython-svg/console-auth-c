package source.repository;
import source.model.Email;
import source.model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepositoryLocalImpl implements UserRepositoryInterface{

    private List<User> users = new ArrayList<>();

    @Override
    public void addUser(User user) {
        users.add(user);
        System.out.println("Se agregó un nuevo usuario");
    }

    @Override
    public Optional<User> searchByEmail(Email email) {
        return users.stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst();
    }
}
