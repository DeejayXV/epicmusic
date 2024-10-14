package com.epicmusic.controllers;

import com.epicmusic.dto.UserDTO;
import com.epicmusic.dto.UserResponseDTO;
import com.epicmusic.entities.User;
import com.epicmusic.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserDTO userDTO) {
        User user = userService.register(userDTO);
        return new ResponseEntity<>(new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail()), HttpStatus.CREATED);
    }
}
