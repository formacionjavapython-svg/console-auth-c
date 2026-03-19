package application.service;

import application.dto.AuthResponseDTO;
import application.dto.LoginDTO;
import application.dto.RegisterDTO;
import application.validator.AuthValidator;
import domain.model.IUserRepository;
import domain.model.User;
import application.dto.AuthStatus;
import util.PasswordUtil;

public class AuthService implements IAuthService {

    private IUserRepository repository;

    public AuthService(IUserRepository repository) {
        this.repository = repository;
    }


    @Override
    public AuthResponseDTO login(LoginDTO request) {
        AuthResponseDTO response = new AuthResponseDTO();
        User user = repository.findByEmail(request.email);
        if (user == null) {
            response.status = AuthStatus.USER_NOT_FOUND;
            response.message = "El email no existe";
            return response;
        }
        if (!PasswordUtil.verify(request.password, user.getPassword())) {
            response.status = AuthStatus.INVALID_PASSWORD;
            response.message = "Password incorrecto";
            return response;
        }

        response.status = AuthStatus.SUCCESS;
        response.message = "Login exitoso";

        return response;
    }

    @Override
    public AuthResponseDTO register(RegisterDTO request) {
        try {
            AuthValidator.validateRegister(request.email, request.password);
            AuthResponseDTO response = new AuthResponseDTO();
            System.out.println(request.email+" | "+request.password);
            if (repository.findByEmail(request.email) != null) {
                response.status = AuthStatus.USER_ALREADY_EXISTS;
                response.message = "El usuario ya existe";
                return response;
            }

            String hashed = PasswordUtil.hash(request.password);
            User user = new User(request.email, hashed);
            repository.save(user);

            response.status = AuthStatus.SUCCESS;
            response.message = "Usuario registrado correctamente";
            return response;
        } catch (IllegalArgumentException e) {return new AuthResponseDTO(
                    AuthStatus.VALIDATION_ERROR,
                    e.getMessage()
            );
        }
    }
}
