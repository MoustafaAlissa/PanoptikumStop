package com.example.panoptikumstop.security.token;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
/**
 * Der ConfirmationTokenService ist verantwortlich für die Verwaltung von Bestätigungstoken
 * im Zusammenhang mit der Benutzerauthentifizierung.
 */
@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;
    /**
     * Speichert ein Bestätigungstoken in der Datenbank.
     *
     * @param token Das zu speichernde Bestätigungstoken.
     */
    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepository.save(token);
    }
    /**
     * Ruft ein Bestätigungstoken anhand seines Werts ab.
     *
     * @param token Der Wert des zu suchenden Bestätigungstokens.
     * @return Ein Optional, das das gefundene Bestätigungstoken enthält, wenn vorhanden.
     */
    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    /**
     * Legt das Bestätigungsdatum für ein Bestätigungstoken fest.
     *
     * @param token Das zu bestätigende Token.
     * @return Die Anzahl der aktualisierten Datensätze (sollte normalerweise 1 sein).
     */

    public int setConfirmedAt(String token) {
        return confirmationTokenRepository.updateConfirmedAt(
                token, LocalDateTime.now());
    }
}
