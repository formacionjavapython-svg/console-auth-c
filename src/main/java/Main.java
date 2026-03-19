import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;
import  java.util.*;
public class Main {



    public static void main(String[] args) {
        boolean salir=false;
        boolean ingreso=false;
        boolean registrado=false;
        String correo="";
        String password="";
        Scanner sc = new Scanner(System.in);
        int contador=0;
        do {

                System.out.println("\n---  REGISTROS ---");
                System.out.println("1. Registrar nuevo correo");
                System.out.println("Ingrese correo");
                 correo = sc.nextLine();
                System.out.println("Ingresa una contraseña");
                 password = sc.nextLine();
                if (ValidaEmail.validarPassword(password)) {

                    registrado=true;
                    salir=true;
                } else {
                    System.out.println("La contraseña de ser de al menos 8 caracteres con tener al menos un digito, debe contener al menos una mayuscula y minuscula.");
                    System.out.println("Desea intentarlo de nuevo? s/n");
                    String rest = sc.nextLine();
                    if(rest.equals("n"))
                    {
                        salir=true;
                    }

                }

        }while (!salir);
        if (registrado)
        {
            Usuarios usuario = new Usuarios(correo, password);
            do {
                System.out.println("\n---  Inicio de sesion ---");
                System.out.println("Ingrese correo");
                String correoLog = sc.nextLine();
                System.out.println("Ingresa una contraseña");
                String intentoPass = sc.nextLine();
                String passwordguardado= usuario.GetPassword();
                if (correoLog.equals(usuario.GetEmail())) {
                    if (PasswordHasher.verificar(intentoPass, passwordguardado)) {
                        System.out.println("Acceso concedido");
                        contador=3;

                    } else {
                        System.out.println("Password invalido.");
                        contador++;
                    }
                }else {
                    System.out.println("Correo invalido.");
                    contador++;
                }

            }while (contador<3);

        }



    }

}
