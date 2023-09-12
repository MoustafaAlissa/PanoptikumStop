package com.example.panoptikumstop.security.jwt;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Die Klasse CustomAuthenticationEntryPoint ist eine Implementierung des Spring Security AuthenticationEntryPoint-Interfaces.
 * Sie wird verwendet, um die Aktionen zu definieren, die bei einer nicht authentifizierten Anfrage
 * ausgelöst werden sollen, um eine unbefugte Anfrage zu behandeln.
 *
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * Behandelt eine nicht authentifizierte Anfrage, indem eine HTTP-UNAUTHORIZED (401) Antwort mit
     * der Meldung "Invalid token" gesendet wird.
     *
     * @param request       Das HTTP-Anfragenobjekt.
     * @param response      Das HTTP-Antwortobjekt.
     * @param authException Die Ausnahme, die die Authentifizierungsfehlerinformationen enthält.
     * @throws IOException  Eine Ausnahme, die bei Fehlern beim Schreiben der Antwort ausgelöst wird.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
    }
}