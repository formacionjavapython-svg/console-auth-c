public class Usuarios {
    private String email;
    private String Contrasena;
    public Usuarios(String email,String Contrasena)
    {
        this.email=email;
        byte[] salt = PasswordHasher.generarSalt();
        this.Contrasena=  PasswordHasher.hashPassword(Contrasena, salt);
    }
    public String GetEmail(){ return this.email;}
    public String GetPassword(){return Contrasena;}

}
