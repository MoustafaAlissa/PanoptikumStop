package com.example.panoptikumstop.security.jwt;

import com.example.panoptikumstop.security.MyUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * Die Klasse JwtAuthFilter ist ein Spring Security-Filter, der JWT-Authentifizierung für eingehende HTTP-Anfragen ermöglicht.
 * Sie überprüft das JWT-Token in der Anfrage und authentifiziert den Benutzer, falls ein gültiges Token gefunden wird.
 *
 */

public class JwtAuthFilter extends OncePerRequestFilter {
    private JwtUtils jwtUtils;
    private MyUserDetailsService userDetailsService;
    /**
     * Konstruktor für den JwtAuthFilter.
     *
     * @param jwtUtils         Das JwtUtils-Objekt zur Validierung von JWT-Token.
     * @param userDetailsService Der MyUserDetailsService zur Laden von Benutzerdetails anhand des Benutzernamens.
     */
    public JwtAuthFilter(JwtUtils jwtUtils, MyUserDetailsService userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }
    /**
     * Diese Methode wird für jede eingehende HTTP-Anfrage aufgerufen und überprüft das JWT-Token.
     * Wenn ein gültiges Token gefunden wird, wird der Benutzer authentifiziert und in den Sicherheitskontext gesetzt.
     *
     * @param request      Das HTTP-Anfragenobjekt.
     * @param response     Das HTTP-Antwortobjekt.
     * @param filterChain  Der Filter-Chain für die weitere Verarbeitung der Anfrage.
     * @throws ServletException Eine Ausnahme, die bei Servlet-Fehlern ausgelöst wird.
     * @throws IOException      Eine Ausnahme, die bei Eingabe-/Ausgabefehlern ausgelöst wird.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwtToken = getJwtTokenFromRequest(request);

            if (jwtToken != null && jwtUtils.validateJwtToken(jwtToken)) {
                String username = jwtUtils.getUsernameFromJwtToken(jwtToken);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            // Handle JWT validation exception
        }

        filterChain.doFilter(request, response);
    }
    /**
     * Extrahiert das JWT-Token aus dem "Authorization" Header der HTTP-Anfrage.
     *
     * @param request Das HTTP-Anfragenobjekt.
     * @return Das JWT-Token als Zeichenkette oder null, wenn kein Token gefunden wurde.
     */
    private String getJwtTokenFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            return header.replace("Bearer ", "");
        }

        return null;
    }
}