
package sistema;

public class Usuario {
   private Email email;
   private Password password;

    public Usuario() {
    }

    public Usuario(Email email, Password password) {
        this.email = email;
        this.password = password;
       
    }

    public Email getEmail() {
        return email;
    }

    public Password getPassword() {
        return password;
    }
    
    
    
   
   
}
