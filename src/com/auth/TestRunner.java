package com.auth;

import com.auth.policy.*;
import com.auth.repository.InMemoryUserRepository;
import com.auth.repository.UserRepository;
import com.auth.service.AuthService;

import java.util.ArrayList;
import java.util.List;

public class TestRunner {

    public static void main(String[] args) {

        List<String> results = new ArrayList<>();

        UserRepository repo = new InMemoryUserRepository();

        PasswordPolicy policy = new PasswordPolicy();
        policy.addRule(new MinLengthRule(6));
        policy.addRule(new ContainsNumberRule());

        AuthService authService = new AuthService(repo, policy);
        authService.register("test@gmail.com", "abc123");
        results.add("Registro exitoso: OK");

        boolean loginOk = authService.login("test@gmail.com", "abc123");
        results.add("Login correcto: " + (loginOk ? "OK" : "FAIL"));

        boolean loginFail = authService.login("test@gmail.com", "000000");
        results.add("Login incorrecto: " + (!loginFail ? "OK" : "FAIL"));

        authService.register("test2@gmail.com", "abc");
        results.add("Password inválido: OK");

        System.out.println("\n--- RESULTADOS ---");
        for (String result : results) {
            System.out.println(result);
        }
    }
}