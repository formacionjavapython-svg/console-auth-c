
package com.auth;

import com.auth.policy.*;
import com.auth.repository.InMemoryUserRepository;
import com.auth.repository.UserRepository;
import com.auth.service.AuthService;

public class Main {

    public static void main(String[] args) {
        System.out.println(System.getProperty("java.version"));
        UserRepository repo = new InMemoryUserRepository();

        PasswordPolicy policy = new PasswordPolicy();
        policy.addRule(new MinLengthRule(6));
        policy.addRule(new ContainsNumberRule());

        AuthService authService = new AuthService(repo, policy);

        ConsoleApp app = new ConsoleApp(authService);
        app.start();
    }
}
