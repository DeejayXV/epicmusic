package com.epicmusic.controllers;

import com.epicmusic.dto.AuthRequest;
import com.epicmusic.security.JwtService;
import com.epicmusic.exception.AuthenticationFailureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/users")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            if (authentication.isAuthenticated()) {
                return ResponseEntity.ok(jwtService.generateToken(authRequest.getUsername()));
            } else {
                throw new AuthenticationFailureException("Authentication failed");
            }
        } catch (AuthenticationException e) {
            throw new AuthenticationFailureException("Invalid credentials", e);
        }
    }
}