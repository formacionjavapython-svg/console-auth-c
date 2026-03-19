package application.dto;

public class RegisterDTO {
    public String email;
    public String password;

    public RegisterDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
