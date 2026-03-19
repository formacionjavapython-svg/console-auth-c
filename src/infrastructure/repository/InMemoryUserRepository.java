package infrastructure.repository;

import domain.model.IUserRepository;
import domain.model.User;

import java.util.ArrayList;
import java.util.List;

public class InMemoryUserRepository  implements IUserRepository {
    private final List<User> users = new ArrayList<>();

    @Override
    public void save(User user) {
        users.add(user);
    }

    @Override
    public User findByEmail(String email) {
        return users.stream()
                .filter(u -> email.equals(u.getEmail()))
                .findFirst()
                .orElse(null);
    }

    public void clearAll() {
        users.clear();
    }
}
