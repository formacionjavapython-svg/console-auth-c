//package com.auth;
//
//import com.auth.domain.Email;
//
//public class Main {
//
//    public static void main(String[] args) {
//        Email email = new Email("testmail.com");
//        System.out.println("Email creado: " + email);
//    }
//}
//
//package com.auth;
//
//import com.auth.domain.Email;
//import com.auth.domain.User;
//
//public class Main {
//
//    public static void main(String[] args) {
//
//        Email email = new Email("test@gmail.com");
//        User user = new User(email, "HASH_FAKE_123");
//
//        System.out.println("Usuario creado: " + user.getEmail());
//    }
//}
//
//package com.auth;
//
//import com.auth.domain.Email;
//import com.auth.domain.User;
//import com.auth.repository.InMemoryUserRepository;
//import com.auth.repository.UserRepository;
//
//public class Main {
//
//    public static void main(String[] args) {
//
//        UserRepository repo = new InMemoryUserRepository();
//
//        Email email = new Email("test@gmail.com");
//        User user = new User(email, "HASH_FAKE_123");
//
//        repo.save(user);
//
//        var foundUser = repo.findByEmail(email);
//
//        if (foundUser.isPresent()) {
//            System.out.println("Usuario encontrado: " + foundUser.get().getEmail());
//        } else {
//            System.out.println("Usuario no encontrado");
//        }
//    }
//}
//
//package com.auth;
//
//import com.auth.policy.*;
//import com.auth.repository.InMemoryUserRepository;
//import com.auth.repository.UserRepository;
//import com.auth.service.AuthService;
//
//public class Main {
//
//    public static void main(String[] args) {
//
//        UserRepository repo = new InMemoryUserRepository();
//
//        PasswordPolicy policy = new PasswordPolicy();
//        policy.addRule(new MinLengthRule(6));
//        policy.addRule(new ContainsNumberRule());
//
//        AuthService authService = new AuthService(repo, policy);
//
//        authService.register("test@gmail.com", "abc");
//        authService.register("test@gmail.com", "abc123");
//    }
//}

//
//package com.auth;
//
//import com.auth.policy.*;
//import com.auth.repository.InMemoryUserRepository;
//import com.auth.repository.UserRepository;
//import com.auth.service.AuthService;
//
//public class Main {
//
//    public static void main(String[] args) {
//
//        UserRepository repo = new InMemoryUserRepository();
//
//        PasswordPolicy policy = new PasswordPolicy();
//        policy.addRule(new MinLengthRule(6));
//        policy.addRule(new ContainsNumberRule());
//
//        AuthService authService = new AuthService(repo, policy);
//
//        authService.register("test@gmail.com", "abc123");
//
//        boolean ok = authService.login("test@gmail.com", "abc123");
//        System.out.println("Login correcto: " + ok);
//
//        boolean fail = authService.login("test@gmail.com", "000000");
//        System.out.println("Login incorrecto: " + fail);
//    }
//}

package com.auth;

import com.auth.policy.*;
import com.auth.repository.InMemoryUserRepository;
import com.auth.repository.UserRepository;
import com.auth.service.AuthService;

public class Main {

    public static void main(String[] args) {

        UserRepository repo = new InMemoryUserRepository();

        PasswordPolicy policy = new PasswordPolicy();
        policy.addRule(new MinLengthRule(6));
        policy.addRule(new ContainsNumberRule());

        AuthService authService = new AuthService(repo, policy);

        ConsoleApp app = new ConsoleApp(authService);
        app.start();
    }
}