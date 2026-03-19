package application.dto;
import java.io.Serializable;

public class AuthResponseDTO {
    public AuthStatus status;
    public String message;

    public AuthResponseDTO() {
        this.status = status;
        this.message = message;
    }

    public AuthResponseDTO(AuthStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public AuthStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
