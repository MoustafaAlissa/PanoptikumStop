package com.example.panoptikumstop.model.dto;
/**
 * Die Klasse AuthResponse stellt eine Datenübertragungsklasse (DTO) dar, die die Antwort auf eine Authentifizierungsanforderung repräsentiert.
 * Sie enthält das JWT-Token, Informationen zur Datenfreigabe und die Rolle des authentifizierten Benutzers.
 *
 */
public record AuthResponse(String token, boolean datenSpenden, String role) {
    // Die Klasse ist ein "record", daher sind keine weiteren Methoden oder Felder erforderlich.
}
