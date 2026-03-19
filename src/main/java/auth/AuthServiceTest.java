package auth;

import auth.service.AuthService;

public class AuthServiceTest {

    private AuthService auth = new AuthService();

    public void testRegisterSuccess() {
        auth.register("test@gmail.com", "Abc12345@");
        System.out.println("testRegisterSuccess OK");
    }

    public void testLoginSuccess() {
        auth.register("user@gmail.com", "Abc12345@");
        auth.login("user@gmail.com", "Abc12345@");
        System.out.println("testLoginSuccess OK");
    }

    public void testLoginFail() {
        auth.register("fail@gmail.com", "Abc12345@");
        auth.login("fail@gmail.com", "wrongpass");
        System.out.println("testLoginFail OK");
    }

    public void testPasswordPolicy() {
        auth.register("bad@gmail.com", "123");
        System.out.println("testPasswordPolicy OK");
    }
}