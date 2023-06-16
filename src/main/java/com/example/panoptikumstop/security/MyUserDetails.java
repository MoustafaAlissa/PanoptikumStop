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

public class MyUserDetails implements UserDetails {


    private final User user;


    public MyUserDetails(User user) {
        this.user = user;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(
                user.getRole());
        return Arrays.asList(authority);
    }


    @Override
    public String getPassword() {
        return user.getPassword();
    }


    @Override
    public String getUsername() {
        return user.getEmail();
    }


    public String getEmail() {
        return user.getEmail();
    }


    public Long getId() {
        return user.getId();
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }


    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    @Override
    public boolean isEnabled() {
        return true;
    }
}
