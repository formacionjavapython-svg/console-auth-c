package domain.repository;

import domain.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private List<User> users = new ArrayList<>();

    public void save(User user) {
        users.add(user);
    }

    public User findByUsername(String username) {
        return users.stream()
                .filter(u -> u.getEmail().equals(username))
                .findFirst()
                .orElse(null);
    }
}
