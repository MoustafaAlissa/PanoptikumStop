/*
 * Autoren: Leon Maschlanka, Lukas Maschlanka
 *
 * Dieser Service wird genutzt, um alle Benutzerdaten bezüglich der
 * Authentifikation zu erhalten.
 */
package com.example.panoptikumstop.security;

import com.example.panoptikumstop.model.entity.User;
import com.example.panoptikumstop.repo.UserRepo;
import com.example.panoptikumstop.security.token.ConfirmationToken;
import com.example.panoptikumstop.security.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


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

//
//    public String signUpUser(User user) {
//        boolean userExists = userRepository
//                .findByEmail(user.getEmail())
//                .isPresent();
//
//        if (userExists) {
//            throw new IllegalStateException(EMAIL_IS_TAKEN);
//        }
//
//
//        String encodedPassword = bCryptPasswordEncoder
//                .encode(user.getPassword());
//
//        user.setPassword(encodedPassword);
//
//        userRepository.save(user);
//        var token = UUID.randomUUID()
//                .toString();
//
//        var confirmationToken = new ConfirmationToken(
//                token,
//                LocalDateTime.now(),
//                LocalDateTime.now()
//                        .plusMinutes(15),
//                user
//        );
//
//        confirmationTokenService.saveConfirmationToken(
//                confirmationToken);
//        return token;
//    }


}
