package com.example.panoptikumstop.security.token;

import com.example.panoptikumstop.model.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
/**
 * Die Klasse ConfirmationToken repräsentiert ein Bestätigungstoken, das zur Bestätigung der
 * E-Mail-Adresse eines Benutzers verwendet wird.
 *
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@ToString
public class ConfirmationToken {

    @SequenceGenerator(
            name = "confirmation_token_sequence",
            sequenceName = "confirmation_token_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "confirmation_token_sequence"
    )
    private Long id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "user_ID"
    )
    private User user;
    /**
     * Konstruktor für die ConfirmationToken-Klasse.
     *
     * @param token      Das Bestätigungstoken.
     * @param createdAt  Das Erstellungsdatum des Tokens.
     * @param expiresAt  Das Ablaufdatum des Tokens.
     * @param user       Der Benutzer, dem das Token zugeordnet ist.
     */
    public ConfirmationToken(String token,
                             LocalDateTime createdAt,
                             LocalDateTime expiresAt,
                             User user) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.user = user;
    }
}
