public class Main {
    public static void main(String[] args) {

        AuthService auth = new AuthService();

        auth.register("andres@mail.com", "1234");

        boolean result = auth.login("andres@mail.com", "1234");

        System.out.println("Login: " + result);
    }
}