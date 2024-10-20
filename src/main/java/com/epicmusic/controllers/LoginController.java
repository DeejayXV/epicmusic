package com.epicmusic.controllers;

import com.epicmusic.dto.AuthenticationRequest;
import com.epicmusic.security.JwtService;
import com.epicmusic.exception.AuthenticationFailureException;
import com.epicmusic.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/users")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthenticationRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            if (authentication.isAuthenticated()) {
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(authRequest.getUsername());
                String token = jwtService.generateToken(userDetails);
                return ResponseEntity.ok(token);
            } else {
                throw new AuthenticationFailureException("Authentication failed");
            }
        } catch (AuthenticationException e) {
            throw new AuthenticationFailureException("Invalid credentials", e);
        }
    }
}
