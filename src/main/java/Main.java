import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AuthService auth = new AuthService();

        while (true) {
            System.out.println("\n1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            int option = sc.nextInt();
            sc.nextLine();

            if (option == 1) {
                System.out.print("Email: ");
                String email = sc.nextLine();

                System.out.print("Password: ");
                String password = sc.nextLine();

                auth.register(email, password);

            } else if (option == 2) {
                System.out.print("Email: ");
                String email = sc.nextLine();

                System.out.print("Password: ");
                String password = sc.nextLine();

                auth.login(email, password);

            } else if (option == 3) {
                break;
            }
        }
    }
}