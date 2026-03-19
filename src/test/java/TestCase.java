public class TestCase {
    
    public void testRegisterSuccess() {
        System.out.println("Testing register success...");
        AuthService authService = new AuthService();
        
        // Test 1: Valid registration
        String email = "test@example.com";
        String password = "Password123";
        
        // Validate password
        var errors = authService.validatePassword(password, email);
        if (!errors.isEmpty()) {
            throw new AssertionError("Password validation failed: " + errors);
        }
        
        // Register user
        authService.register(email, password);
        
        // Verify user was registered
        var user = authService.findUser(email);
        if (user.isEmpty()) {
            throw new AssertionError("User not found after registration");
        }
        
        System.out.println("  User registered successfully: " + email);
    }
    
    public void testLoginSuccess() {
        System.out.println("Testing login success...");
        AuthService authService = new AuthService();
        
        // Register a user first
        String email = "login@test.com";
        String password = "TestPass123";
        
        authService.register(email, password);
        
        // Try to find the user (simulate login)
        var user = authService.findUser(email);
        if (user.isEmpty()) {
            throw new AssertionError("Login failed: user not found");
        }
        
        System.out.println("  Login successful for: " + email);
    }
}