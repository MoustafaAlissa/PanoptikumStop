package com.example.panoptikumstop.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
/**
 * Die Klasse JwtUtils stellt Dienstprogrammfunktionen für die Verarbeitung von JWT (JSON Web Token) bereit.
 * Sie ermöglicht das Generieren von JWT-Token, das Extrahieren des Benutzernamens aus einem Token und die Validierung von JWT-Token.
 */
@Component
public class JwtUtils {


    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token-expiration-time}")
    private int expirationTime;
    /**
     * Generiert ein JWT-Token für den angegebenen Benutzernamen.
     *
     * @param username Der Benutzername, für den das Token generiert wird.
     * @return Das generierte JWT-Token als Zeichenkette.
     */
    public String generateJwtToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }
    /**
     * Extrahiert den Benutzernamen aus einem JWT-Token.
     *
     * @param token Das JWT-Token, aus dem der Benutzername extrahiert wird.
     * @return Der Benutzername, der aus dem Token extrahiert wurde.
     */
    public String getUsernameFromJwtToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
    /**
     * Überprüft die Gültigkeit eines JWT-Tokens.
     *
     * @param token Das zu überprüfende JWT-Token.
     * @return true, wenn das Token gültig ist, andernfalls false.
     */

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            // Token validation failed
            return false;
        }
    }

}

