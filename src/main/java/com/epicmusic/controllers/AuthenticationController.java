package com.epicmusic.controllers;

import com.epicmusic.dto.AuthenticationRequest;
import com.epicmusic.dto.AuthenticationResponse;
import com.epicmusic.dto.RegisterRequest;
import com.epicmusic.entities.User;
import com.epicmusic.repositories.UserRepository;
import com.epicmusic.security.JwtService;
import com.epicmusic.services.CustomUserDetailsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email già in uso");
        }
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username già in uso");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("Utente registrato con successo");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Username o password non corretti"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Username o password non corretti");
        }
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getUsername());
        String token = jwtService.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }
}
