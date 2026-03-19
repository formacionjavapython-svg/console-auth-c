
package sistema;

import sistema.Rules.PasswordPolicy;
import java.util.List;
public class Password {
    private String valor;

    public Password(String valor, String email, PasswordPolicy policy) {

        List<String> errores = policy.validate(valor, email);

        if (!errores.isEmpty()) {
            throw new IllegalArgumentException(errores.toString());
        }

        this.valor = valor;
    }
       

    public String getValor() {
        return valor;
    }


    }
    
   
    

