package com.consoleauthlab.service;

import com.consoleauthlab.model.Email;
import com.consoleauthlab.model.User;
import com.consoleauthlab.repository.UserRepository;

/**
 * Servicio principal de autenticación.
 */
public class AuthService {
    
    private final UserRepository userRepository;
    private final HashService hashService;
    
    public AuthService(UserRepository userRepository, HashService hashService) {
        this.userRepository = userRepository;
        this.hashService = hashService;
    }
    
    /**
     * Registra un nuevo usuario.
     */
    public boolean register(String email, String password) {
        try {
            // Validar email
            Email emailObj = new Email(email);
            
            // Verificar si el usuario ya existe
            if (userRepository.existsByEmail(email)) {
                return false;
            }
            
            // Validar política de contraseña
            if (!hashService.validatePasswordPolicy(password)) {
                return false;
            }
            
            // Generar hash de contraseña
            String passwordHash = hashService.generateHash(password);
            
            // Crear y guardar usuario
            User user = new User(emailObj, passwordHash);
            userRepository.save(user);
            
            return true;
            
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
    
    /**
     * Autentica un usuario.
     */
    public boolean login(String email, String password) {
        try {
            // Validar email
            new Email(email);
            
            // Buscar usuario
            return userRepository.findByEmail(email)
                .map(user -> hashService.verifyHash(password, user.getPasswordHash()))
                .orElse(false);
                
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
    
    /**
     * Cambia la contraseña de un usuario.
     */
    public boolean changePassword(String email, String oldPassword, String newPassword) {
        try {
            // Validar email
            new Email(email);
            
            // Verificar credenciales actuales
            if (!login(email, oldPassword)) {
                return false;
            }
            
            // Validar nueva política de contraseña
            if (!hashService.validatePasswordPolicy(newPassword)) {
                return false;
            }
            
            // Actualizar contraseña
            return userRepository.findByEmail(email)
                .map(user -> {
                    String newPasswordHash = hashService.generateHash(newPassword);
                    user.setPasswordHash(newPasswordHash);
                    userRepository.save(user);
                    return true;
                })
                .orElse(false);
                
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}