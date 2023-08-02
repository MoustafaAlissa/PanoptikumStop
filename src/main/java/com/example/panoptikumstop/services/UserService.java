package com.example.panoptikumstop.services;


import com.example.panoptikumstop.exceptions.DuplicatedUserInfoException;
import com.example.panoptikumstop.exceptions.TokenExpiredException;
import com.example.panoptikumstop.exceptions.UserExistException;
import com.example.panoptikumstop.model.dto.AuthResponse;
import com.example.panoptikumstop.model.dto.LogInDto;
import com.example.panoptikumstop.model.dto.UserDto;
import com.example.panoptikumstop.model.entity.User;
import com.example.panoptikumstop.repo.UserRepo;
import com.example.panoptikumstop.security.email.EmailSender;
import com.example.panoptikumstop.security.email.Emails;
import com.example.panoptikumstop.security.jwt.JwtUtils;
import com.example.panoptikumstop.security.token.ConfirmationToken;
import com.example.panoptikumstop.security.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private static final String EMAIL_IS_TAKEN = "email already taken";
    private static final String EMAIL_NOT_EXIST = "email not exist";
    private static final String LOGIN_SUCCESSFUL = "Erfolg bei einlogen von ";
    private static final String PASSWRORD_OR_EMAIL_ERROR = "Password oder Email ist falsch";
    private static final String URL_PASSWORD_FORGET = "http://localhost:8080/auth/confirm?token=%s&mail=%s";
    private static final String TOKEN_EXPIRED = "token expired";
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailSender emailSender;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private AuthenticationManager authenticationManager;
    private ConfirmationTokenService confirmationTokenService;

    public User findByEmail(String email) {
        Optional<User> oUser = Optional.ofNullable(userRepo.findByEmail(email).orElseThrow(() -> new UserExistException("Der Benutzer konnte nicht gefunden werden.")));

        return oUser.get();
    }

    public AuthResponse signIn(LogInDto logInDto) {
        User user = findByEmail(logInDto.getEmail());

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(logInDto.getEmail(), logInDto.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info(LOGIN_SUCCESSFUL + logInDto.getEmail());


        } catch (BadCredentialsException ex) {
            throw new UserExistException(PASSWRORD_OR_EMAIL_ERROR);
        }

        String token = jwtUtils.generateJwtToken(user.getEmail());
        String role = user.getRole();

        return new AuthResponse(token, user.getFirstname(), role);
    }

    public AuthResponse signup(UserDto userDto) {


        boolean userExists = userRepo.existsByEmail(userDto.getEmail());
        if (userExists) {
            throw new DuplicatedUserInfoException(EMAIL_IS_TAKEN);
        }

        User u = User.builder()
                .email(userDto.getEmail())
                .password(bCryptPasswordEncoder.encode(userDto.getPassword()))
                .lastname(userDto.getLastname())
                .firstname(userDto.getFirstname())
                .role("USER")
                .build();

        userRepo.save(u);

        String token = jwtUtils.generateJwtToken(u.getEmail());
        emailSender.sendRegistrationEmail(userDto.getEmail(), Emails.buildRegistrationEmail(userDto.getFirstname()));

        return new AuthResponse(token, u.getFirstname(), u.getRole());
    }

    public void PasswordForget(UserDto userDto) {
        boolean userExists = userRepo.findByEmail(userDto.getEmail()).isPresent();

        if (!userExists) {
            throw new UserExistException(EMAIL_NOT_EXIST);
        }
        User user = findByEmail(userDto.getEmail());

        var token = UUID.randomUUID().toString();

        var confirmationToken = new ConfirmationToken(token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), user);

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        var link = String.format(URL_PASSWORD_FORGET, token, userDto.getEmail());
        emailSender.sendResetPasswordEmail(userDto.getEmail(), Emails.PasswordResetMassage(user.getFirstname(), link));


    }

    public void PasswordReset(UserDto userDto) {
        User user = findByEmail(userDto.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        userRepo.save(user);


        emailSender.InfoEmail(user.getEmail(), Emails.InfoEmail(user.getFirstname()));

    }

    @Transactional
    public void confirmToken(String token) {
        var confirmationToken = confirmationTokenService.getToken(token).orElseThrow(() -> new IllegalStateException("token not found"));

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new TokenExpiredException(TOKEN_EXPIRED);
        }

    }

    public String addAdmin(String email) {
        User u = findByEmail(email);
        u.setRole("ADMIN");
        userRepo.save(u);
        return u.getFirstname() + " " + u.getLastname() + " ";
    }

    public String removeAdmin(String email) {
        User u = findByEmail(email);
        u.setRole("USER");
        userRepo.save(u);

        return u.getFirstname() + " " + u.getLastname();
    }

    public boolean isActive(String token) {
    return jwtUtils.validateJwtToken(token);

    }
}
