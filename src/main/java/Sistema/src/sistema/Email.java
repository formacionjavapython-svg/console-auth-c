
package sistema;

public class Email {
    private String valor;

    public Email(String valor) {
        if(valor.contains("@")){
            this.valor = valor;
        }else{
            throw new IllegalArgumentException("El email no es valido");
        }
        
    }
   
    public String getValor() {
        return valor;
    }
    
}
