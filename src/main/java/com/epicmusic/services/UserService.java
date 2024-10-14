package com.epicmusic.services;

import com.epicmusic.dto.UserDTO;
import com.epicmusic.entities.User;
import com.epicmusic.repositories.UserRepository;
import com.epicmusic.exception.InvalidPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User register(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        validatePassword(userDTO.getPassword());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        return userRepository.save(user);
    }

    private void validatePassword(String password) {
        if (password.length() < 8) {
            throw new InvalidPasswordException("Password must be at least 8 characters long");
        }
        if (!password.matches(".*[A-Z].*") || !password.matches(".*[a-z].*") || !password.matches(".*\d.*")) {
            throw new InvalidPasswordException("Password must contain at least one uppercase letter, one lowercase letter, and one digit");
        }
    }
}