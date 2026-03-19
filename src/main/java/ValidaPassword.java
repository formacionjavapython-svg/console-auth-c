public class ValidaPassword {
    public static boolean esSegura(String pass) {
        if (pass.length() < 8) return false;

        boolean tieneMayuscula = false;
        boolean tieneMinuscula = false;
        boolean tieneNumero = false;
        boolean tieneEspecial = false;

        for (char c : pass.toCharArray()) {
            if (Character.isUpperCase(c)) tieneMayuscula = true;
            else if (Character.isLowerCase(c)) tieneMinuscula = true;
            else if (Character.isDigit(c)) tieneNumero = true;
            else tieneEspecial = true;
        }

        return tieneMayuscula && tieneMinuscula && tieneNumero && tieneEspecial;
    }

}
