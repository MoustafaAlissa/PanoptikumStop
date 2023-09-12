package com.example.panoptikumstop.security.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
/**
 * Das ConfirmationTokenRepository ist eine JPA-Repository-Schnittstelle zur Datenbankinteraktion
 * für ConfirmationToken-Entitäten.
 */
@Repository
@Transactional(readOnly = true)
public interface ConfirmationTokenRepository
        extends JpaRepository<ConfirmationToken, Long> {
    /**
     * Sucht ein ConfirmationToken anhand seines Tokens.
     *
     * @param token Der Wert des zu suchenden Tokens.
     * @return Ein Optional, das das gefundene ConfirmationToken enthält, wenn vorhanden.
     */
    Optional<ConfirmationToken> findByToken(String token);
    /**
     * Aktualisiert das Bestätigungsdatum für ein ConfirmationToken anhand seines Tokens.
     *
     * @param token         Der Wert des Tokens, für das das Bestätigungsdatum festgelegt wird.
     * @param confirmedAt   Das Bestätigungsdatum, das festgelegt werden soll.
     * @return Die Anzahl der aktualisierten Datensätze (sollte normalerweise 1 sein).
     */
    @Transactional
    @Modifying
    @Query("UPDATE ConfirmationToken c " +
            "SET c.confirmedAt = ?2 " +
            "WHERE c.token = ?1")
    int updateConfirmedAt(String token,
                          LocalDateTime confirmedAt);
}
