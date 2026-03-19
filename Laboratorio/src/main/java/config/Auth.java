package main.java.config;

public class Auth {
    public static final String PASSWORD_POLICY = "^(?=.*[A-Za-z])(?=.*\\d).{8,}$";
    public static final String PEPPER = System.getenv().getOrDefault("APP_PEPPER", "pepper_secreto_2026");
}

