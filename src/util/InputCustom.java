package util;

import java.util.Scanner;

public class InputCustom {
    private static final Scanner scanner = new Scanner(System.in);

    public static String readLine(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }
}
