package application.service;

import application.dto.AuthResponseDTO;
import application.dto.LoginDTO;
import application.dto.RegisterDTO;

public interface IAuthService {
    AuthResponseDTO login(LoginDTO request);

    AuthResponseDTO register(RegisterDTO request);
}
