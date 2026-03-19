package application.service;

import application.dto.*;
import domain.model.IUserRepository;
import domain.model.User;
import util.PasswordUtil;

import java.util.ArrayList;
import java.util.List;

import static application.dto.AuthStatus.*;

public class AuthServiceTest {

    public static void main(String[] args) {
        System.out.println("Running testLoginSuccess...");
        testLoginSuccess();
        testUserNotFound();
        testInvalidPassword();
        testRegisterSuccess();
        testRegisterUserExists();

        testPasswordTooShort();
        testPasswordContainsEmail();

        System.out.println("Todos los tests pasaron");
    }

    // Fake repository (en lugar de Mockito)
    static class FakeUserRepository implements IUserRepository {

        private List<User> users = new ArrayList<>();

        @Override
        public User findByEmail(String email) {
            return users.stream()
                    .filter(u -> email.equals(u.getEmail()))
                    .findFirst()
                    .orElse(null);
        }

        @Override
        public void clearAll() {
            users.clear();
        }

        @Override
        public void save(User user) {
            users.add(user);
        }
    }

    // LOGIN SUCCESS
    static void testLoginSuccess() {
        FakeUserRepository repo = new FakeUserRepository();

        String email = "test@test.com";
        String password = "1234";

        repo.save(new User(email, PasswordUtil.hash(password)));

        AuthService service = new AuthService(repo);

        AuthResponseDTO response = service.login(new LoginDTO(email, password));

        assert response.getStatus() == SUCCESS;

        repo.clearAll();
    }

    // USER NOT FOUND
    static void testUserNotFound() {
        FakeUserRepository repo = new FakeUserRepository();

        AuthService service = new AuthService(repo);

        AuthResponseDTO response = service.login(
                new LoginDTO("no@user.com", "1234")
        );

        assert response.getStatus() == USER_NOT_FOUND;

        repo.clearAll();
    }

    // INVALID PASSWORD
    static void testInvalidPassword() {
        FakeUserRepository repo = new FakeUserRepository();

        String email = "test@test.com";
        repo.save(new User(email, PasswordUtil.hash("correct")));

        AuthService service = new AuthService(repo);

        AuthResponseDTO response = service.login(
                new LoginDTO(email, "wrong")
        );

        assert response.getStatus() == INVALID_PASSWORD;

        repo.clearAll();
    }

    // REGISTER SUCCESS
    static void testRegisterSuccess() {
        FakeUserRepository repo = new FakeUserRepository();

        AuthService service = new AuthService(repo);

        AuthResponseDTO response = service.register(
                new RegisterDTO("new@test.com", "12345678")
        );

        assert response.getStatus() == SUCCESS;

        repo.clearAll();
    }

    // USER EXISTS
    static void testRegisterUserExists() {
        FakeUserRepository repo = new FakeUserRepository();

        String email = "test@test.com";
        repo.save(new User(email, PasswordUtil.hash("12345678")));

        AuthService service = new AuthService(repo);

        AuthResponseDTO response = service.register(
                new RegisterDTO(email, "12345678910")
        );

        assert response.getStatus() == USER_ALREADY_EXISTS;

        repo.clearAll();
    }
    //PASSWORD CORTA
    static void testPasswordTooShort() {
        FakeUserRepository repo = new FakeUserRepository();
        AuthService service = new AuthService(repo);

        AuthResponseDTO response = service.register(
                new RegisterDTO("test@test.com", "123")
        );

        assert response.getStatus() == VALIDATION_ERROR;
    }

    //EMAIL EN PASSWORD
    static void testPasswordContainsEmail() {
        FakeUserRepository repo = new FakeUserRepository();
        AuthService service = new AuthService(repo);

        AuthResponseDTO response = service.register(
                new RegisterDTO("test@test.com", "test12345")
        );

        assert response.getStatus() == VALIDATION_ERROR;
    }
}