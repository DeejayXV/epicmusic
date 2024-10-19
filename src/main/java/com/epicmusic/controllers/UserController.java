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
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserDTO userDTO) {
        User user = userService.register(userDTO);
        return new ResponseEntity<>(new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail()), HttpStatus.CREATED);
    }

    @GetMapping("/admin")
    public ResponseEntity<String> adminEndpoint() {
        return new ResponseEntity<>("Admin access granted", HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<String> userEndpoint() {
        return new ResponseEntity<>("User access granted", HttpStatus.OK);
    }
}
