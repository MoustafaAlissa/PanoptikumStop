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


@Transactional
@Service
@AllArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepo userRepository;

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
