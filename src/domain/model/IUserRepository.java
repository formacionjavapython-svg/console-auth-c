package domain.model;

public interface IUserRepository {

    void save(User user);

    User findByEmail(String email);

    void clearAll();
}
