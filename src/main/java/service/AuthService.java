package main.java.service;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import main.java.model.Email;
import main.java.model.HashedPassword;
import main.java.model.User;

public class AuthService {
    private User currentUser; // usuario almacenado en memoria, null si no hay nadie logueado/registrado

    public LinkedList<String> login(String email, String password) {
        LinkedList<String> status = new LinkedList<>();

        status = validate(email, password);
        if(status.size() > 0) return status; // validación fallida - pq si tiene valores status, son errores
        
        return status;
    }

    public LinkedList<String> register(String email, String password) {
        if(currentUser != null) logout(); // desloguea al anterior

        LinkedList<String> status = new LinkedList<>();
        status = validate(email, password);
        if(status.size() > 0) return status;

        currentUser = convertCredentialsToUser(email, password);
        return status; // exito
    }

    public void logout() {
        currentUser = null;
    }

    public LinkedList<String> validate(String email, String password) {
        LinkedList<String> errors = new LinkedList<>();
        Email emailObj = null;
        HashedPassword hashedPasswordObj = null;
        try {
            emailObj = new Email(email); // aquí hago validación de formato email
        } catch (Exception e) {
            errors.add(e.getMessage());
        }
        
        try {
            hashedPasswordObj = new HashedPassword(password);
        } catch (Exception e) {
            errors.add(e.getMessage());
        }

        String emailName = email.contains("@") ? email.substring(0, email.indexOf("@")) : email;
        if(password.length() < 8) 
            errors.add("La contraseña debe tener al menos 8 caracteres");
        if(!password.matches(".*\\d.*")) 
            errors.add("La contraseña debe tener al menos un número");
        if(password.contains(emailName)) 
            errors.add("La contraseña no debe contener el nombre del correo");

        if (currentUser != null) { 
            // solo si hay usuario registrado, compara valores
            if(!email.equals(currentUser.getEmail())) 
                errors.add("Correo incorrecto");
            if(!hashedPasswordObj.getHashedPasswordString().equals(currentUser.getHashedPassword())) 
                errors.add("Contraseña incorrecta");
        }
        
        return errors;
    }

    public User convertCredentialsToUser(String email, String password) {
        Email emailObj = new Email(email);
        HashedPassword hashedPasswordObj = new HashedPassword(password);
        return new User(emailObj, hashedPasswordObj);
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
