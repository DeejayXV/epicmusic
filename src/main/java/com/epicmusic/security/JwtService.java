package com.epicmusic.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    // Metodo per ottenere la chiave di firma
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey); // Decodifica della chiave in Base64
        return Keys.hmacShaKeyFor(keyBytes); // Genera la chiave per HS256
    }

    // Estrai il nome utente dal token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Estrai un claim generico dal token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Estrai tutti i claims dal token
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Genera un token per l'utente specificato
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Scadenza 10 ore
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String extractedUsername = extractUsername(token);
        boolean isValid = (extractedUsername.equals(userDetails.getUsername()) && !isTokenExpired(token));
        System.out.println("Token valido? " + isValid);
        return isValid;
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
