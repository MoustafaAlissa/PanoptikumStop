package com.example.panoptikumstop.security.email;
/**
 * Das Interface EmailSender definiert Methoden zur Sendung von E-Mail-Benachrichtigungen für verschiedene Anwendungsfälle.
 * Implementierende Klassen sollten diese Methoden implementieren, um E-Mails für die Benutzerregistrierung,
 * das Zurücksetzen von Passwörtern und andere Benachrichtigungen zu senden.
 */
public interface EmailSender {
    void sendRegistrationEmail(String to, String email);

    void sendResetPasswordEmail(String to, String email);

    void InfoEmail(String to, String email);
}
