/*
 * Autoren: Leon Maschlanka, Lukas Maschlanka
 *
 * Dieser Service wird genutzt, um alle Benutzerdaten bezüglich der
 * Authentifikation zu erhalten.
 */
package com.example.panoptikumstop.security;

import com.example.panoptikumstop.model.entity.User;
import com.example.panoptikumstop.repo.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
/**
 * Dieser Service stellt eine Implementierung des Spring Security UserDetailsService-Interfaces dar.
 * Er wird verwendet, um Benutzerdaten im Zusammenhang mit der Authentifizierung abzurufen.
 */

@Transactional
@Service
@AllArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepo userRepository;
    /**
     * Diese Methode wird aufgerufen, um Benutzerdaten anhand ihrer E-Mail-Adresse abzurufen.
     *
     * @param email Die E-Mail-Adresse des Benutzers, nach der gesucht wird.
     * @return Ein UserDetails-Objekt, das die Benutzerdaten enthält.
     * @throws UsernameNotFoundException Wenn der Benutzer nicht gefunden wird.
     */
    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        Optional<User> oUser = userRepository.findByEmail(email);

        if (oUser.isEmpty()) {
            throw new UsernameNotFoundException("Could not find user");
        }

        return new MyUserDetails(oUser.get());
    }

}
