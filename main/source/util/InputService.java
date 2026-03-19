package source.util;

import java.util.Scanner;

public class InputService {

    private Scanner scanner = new Scanner(System.in);

    public String inputText(String mensaje) {
        System.out.print(mensaje + " ");
        return scanner.nextLine().trim();
    }
}
