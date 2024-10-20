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

    public User register(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        validatePassword(userDTO.getPassword());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(Role.valueOf(userDTO.getRole().toUpperCase()));

        // Salva l'utente nel repository
        userRepository.save(user);

        return user;
    }

    public String authenticate(String username, String password) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new InvalidPasswordException("Password is incorrect");
        }
        return jwtService.generateToken(userDetails);
    }

    private void validatePassword(String password) {
        if (password.length() < 8) {
            throw new InvalidPasswordException("Password must be at least 8 characters long");
        }
        if (!password.matches(".*[A-Z].*") || !password.matches(".*[a-z].*") || !password.matches(".*\\d.*")) {
            throw new InvalidPasswordException("Password must contain at least one uppercase letter, one lowercase letter, and one digit");
        }
    }
}
