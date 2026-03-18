package view;

import java.util.Arrays;


public class ConsoleView {

    public void showMenu() {
        System.out.print("Selecciona una opción: ");
        for (String s : Arrays.asList("\n1. Login", "2. Registro", "3. Salir")) {
            System.out.println(s);
        }
    }
}
