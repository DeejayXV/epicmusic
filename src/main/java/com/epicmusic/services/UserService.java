package com.epicmusic.services;

import com.epicmusic.dto.UserDTO;
import com.epicmusic.entities.User;
import com.epicmusic.entities.Role;
import com.epicmusic.repositories.UserRepository;
import com.epicmusic.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.epicmusic.exception.InvalidPasswordException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    // Metodo per registrare un nuovo utente con i dettagli forniti
    public User register(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        validatePassword(userDTO.getPassword());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(userDTO.getRole() != null ? Role.valueOf(userDTO.getRole().toUpperCase()) : Role.USER);

        return userRepository.save(user);
    }


    // Metodo per autenticare l'utente e generare un token JWT
    public String authenticate(String username, String password) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new InvalidPasswordException("Password is incorrect");
        }
        return jwtService.generateToken(userDetails);
    }

    // Metodo per validare la password durante la registrazione
    private void validatePassword(String password) {
        if (password.length() < 8) {
            throw new InvalidPasswordException("Password must be at least 8 characters long");
        }
        if (!password.matches(".*[A-Z].*") || !password.matches(".*[a-z].*") || !password.matches(".*\\d.*")) {
            throw new InvalidPasswordException("Password must contain at least one uppercase letter, one lowercase letter, and one digit");
        }
    }

    // Metodo per creare un utente con un ruolo di default USER (es. per utenti non registrati da un admin)
    public User createUser(String username, String password, String email) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password)); // Codifica la password
        user.setEmail(email);
        user.setRole(Role.USER); // Assegna un ruolo di default (USER)
        return userRepository.save(user);
    }

}
