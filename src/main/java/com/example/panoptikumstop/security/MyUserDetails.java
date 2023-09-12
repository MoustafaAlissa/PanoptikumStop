/*
 * Autoren: Leon Maschlanka, Lukas Maschlanka
 *
 * Enthält die Benutzerdaten für die Authentifikation.
 */
package com.example.panoptikumstop.security;

import com.example.panoptikumstop.model.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
/**
 * Diese Klasse implementiert das Spring Security UserDetails-Interface und enthält Benutzerdaten
 * für die Authentifizierung.
 *
 */
public class MyUserDetails implements UserDetails {


    private final User user;


    public MyUserDetails(User user) {
        this.user = user;
    }

    /**
     * Gibt die Berechtigungen (Rollen) des Benutzers zurück.
     *
     * @return Eine Liste von GrantedAuthority-Objekten, die die Benutzerberechtigungen repräsentieren.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(
                user.getRole());
        return Arrays.asList(authority);
    }

    /**
     * Gibt das Passwort des Benutzers zurück.
     *
     * @return Das Passwort des Benutzers.
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Gibt den Benutzernamen des Benutzers zurück.
     *
     * @return Der Benutzername des Benutzers (in diesem Fall die E-Mail-Adresse).
     */
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    /**
     * Gibt die E-Mail-Adresse des Benutzers zurück.
     *
     * @return Die E-Mail-Adresse des Benutzers.
     */
    public String getEmail() {
        return user.getEmail();
    }


    /**
     * Gibt die ID des Benutzers zurück.
     *
     * @return Die ID des Benutzers.
     */
    public Long getId() {
        return user.getId();
    }

    /**
     * Gibt an, ob das Benutzerkonto nicht abgelaufen ist.
     *
     * @return true, wenn das Benutzerkonto nicht abgelaufen ist, andernfalls false.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    /**
     * Gibt an, ob das Benutzerkonto nicht gesperrt ist.
     *
     * @return true, wenn das Benutzerkonto nicht gesperrt ist, andernfalls false.
     */

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    /**
     * Gibt an, ob die Anmeldeanmeldeinformationen des Benutzers nicht abgelaufen sind.
     *
     * @return true, wenn die Anmeldeanmeldeinformationen des Benutzers nicht abgelaufen sind, andernfalls false.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    /**
     * Gibt an, ob das Benutzerkonto aktiviert ist.
     *
     * @return true, wenn das Benutzerkonto aktiviert ist, andernfalls false.
     */

    @Override
    public boolean isEnabled() {
        return true;
    }
}
