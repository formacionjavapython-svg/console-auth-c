package presentation.controller;

import application.dto.AuthResponseDTO;
import application.dto.LoginDTO;
import application.dto.RegisterDTO;
import application.service.IAuthService;

public class AuthController {

    private IAuthService authService;

    public AuthController(IAuthService authService) {
        this.authService = authService;
    }
    public void login(String email, String password) {
        LoginDTO dto = new LoginDTO(email, password);
        AuthResponseDTO response = authService.login(dto);
        handleAuthResponse(response);
    }

    public void register(String email, String password) {
        RegisterDTO dto = new RegisterDTO(email, password);
        AuthResponseDTO response = authService.register(dto);
        handleAuthResponse(response);
    }


    private void handleAuthResponse(AuthResponseDTO response) {
        switch (response.status) {
            case SUCCESS:
                System.out.println("" + response.message);
                break;
            case USER_NOT_FOUND:
                System.out.println("Email incorrecto");
                break;
            case INVALID_PASSWORD:
                System.out.println("Password incorrecto");
                break;
            case USER_ALREADY_EXISTS:
                System.out.println("El usuario ya existe");
                break;
            default:
                System.out.println("Resultado desconocido");
        }
    }
}
