package com.epicmusic.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String jwt = null;
        String username = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            try {
                username = jwtService.extractUsername(jwt);
                System.out.println("Extracted JWT: " + jwt);
                System.out.println("Extracted Username: " + username);
            } catch (Exception e) {
                System.out.println("Errore durante l'estrazione del JWT: " + e.getMessage());
            }
        } else {
            System.out.println("Header di autorizzazione non valido o mancante.");
        }

        System.out.println("Auth Header: " + authHeader);
        System.out.println("JWT: " + jwt);
        System.out.println("Username estratto: " + username);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (jwtService.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
                System.out.println("Utente autenticato con successo: " + username);
            } else {
                System.out.println("Token JWT non valido per l'utente " + username);
            }
        } else {
            if (username == null) {
                System.out.println("Username nullo, impossibile autenticare.");
            } else {
                System.out.println("L'autenticazione esiste già nel SecurityContext.");
            }
        }

        filterChain.doFilter(request, response);
    }



}
