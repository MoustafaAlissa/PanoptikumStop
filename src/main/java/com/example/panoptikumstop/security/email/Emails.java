package com.example.panoptikumstop.security.email;

/**
 * Die Klasse Emails enthält Methoden zur Erzeugung von E-Mail-Nachrichten für verschiedene Anwendungsfälle.
 * Sie stellt statische Methoden bereit, um E-Mail-Nachrichten für das Zurücksetzen von Passwörtern,
 * die Benutzerregistrierung und andere Benachrichtigungen zu erstellen.
 */
public class Emails {

    /**
     * Erzeugt eine E-Mail-Nachricht für das Zurücksetzen des Passworts.
     *
     * @param name Der Name des Empfängers.
     * @param link Der Link, über den das Passwort zurückgesetzt werden kann.
     * @return Die E-Mail-Nachricht als HTML-Text.
     */
    public static String PasswordResetMassage(String name, String link) {
        return ("<p>Hallo " + name + " !&nbsp;</p>\n" +
                "<p>Kicken sie auf den Link um die Password zurück zusetzten&nbsp;</p>\n" +
                "<p> " + link + "</p>\n" +
                "<p>best Grüße&nbsp;</p>\n" +
                "<p>PanoptikumStop Team</p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p>&nbsp;</p>");
    }
    /**
     * Erzeugt eine E-Mail-Nachricht zur Bestätigung der Benutzerregistrierung.
     *
     * @param name Der Name des Benutzers.
     * @return Die E-Mail-Nachricht als HTML-Text.
     */
    public static String buildRegistrationEmail(String name) {
        return ("<p>Hallo " + name + " !&nbsp;</p>\n" +
                "<p>Wir freuen uns sehr, dass sie bei angemeldet haben.&nbsp;</p>\n" +
                "<p>best Grüße&nbsp;</p>\n" +
                "<p>PanoptikumStop Team</p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p>&nbsp;</p>");

    }


    /**
     * Erzeugt eine Informations-E-Mail-Nachricht.
     *
     * @param name Der Name des Empfängers.
     * @return Die E-Mail-Nachricht als HTML-Text.
     */
    public static String InfoEmail(String name) {
        return ("<p>Hallo " + name + " !&nbsp;</p>\n" +
                "<p>Sie haben den Password zurückgesetzt.&nbsp;</p>\n" +
                "<p>best Grüße&nbsp;</p>\n" +
                "<p>PanoptikumStop Team</p>\n" +
                "<p>&nbsp;</p>\n" +
                "<p>&nbsp;</p>");
    }
}
