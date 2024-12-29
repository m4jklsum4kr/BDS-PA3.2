package org.but.feec.javafx.services;

import org.but.feec.javafx.api.LibAuthView;
import org.but.feec.javafx.data.LibRepository;
import org.but.feec.javafx.exceptions.ResourceNotFoundException;

import static org.but.feec.javafx.services.Argon2FactoryService.ARGON2;

public class AuthService {

    private LibRepository libRepository;

    public AuthService(LibRepository libRepository) {
        this.libRepository = libRepository;
    }

    private LibAuthView findPersonByEmail(String email) {
        return libRepository.findPersonByEmail(email);
    }

    public boolean authenticate(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            return false;
        }
        LibAuthView personAuthView = findPersonByEmail(username);
        if (personAuthView == null) {
            throw new ResourceNotFoundException("Provided username is not found.");
        }
        return ARGON2.verify(personAuthView.getPassword(), password.toCharArray());
    }

}
