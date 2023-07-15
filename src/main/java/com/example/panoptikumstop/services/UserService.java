package com.example.panoptikumstop.services;


import com.example.panoptikumstop.exceptions.TokenExpiredException;
import com.example.panoptikumstop.exceptions.UserExistException;
import com.example.panoptikumstop.model.dto.UserDto;
import com.example.panoptikumstop.model.entity.User;
import com.example.panoptikumstop.repo.UserRepo;
import com.example.panoptikumstop.security.email.EmailSender;
import com.example.panoptikumstop.security.email.Emails;
import com.example.panoptikumstop.security.token.ConfirmationToken;
import com.example.panoptikumstop.security.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserRepo userRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    private ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;
    private static final String EMAIL_IS_TAKEN = "email already taken";
    private static final String EMAIL_NOT_EXIST = "email not exist";
    private static final String LOGIN_SUCCESSFUL = "Erfolg bei einlogen von ";
    private static final String PASSWRORD_OR_EMAIL_ERROR = "Password oder Email ist falsch";
    private static final String URL_PASSWORD_FORGET = "http://localhost:8080/auth/confirm?token=%s&mail=%s";
    private static final String TOKEN_EXPIRED = "token expired";


    public User findByEmail(String email) {
        Optional<User> oUser = Optional.ofNullable(userRepo.findByEmail(email).orElseThrow(() -> new NoSuchElementException("Der Benutzer konnte nicht gefunden werden.")));

        return oUser.get();
    }
    public void signIn(UserDto userDto) {

        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info(LOGIN_SUCCESSFUL + userDto.getEmail());

        } catch (BadCredentialsException ex) {
            throw new UserExistException(PASSWRORD_OR_EMAIL_ERROR);
        }
    }
    public User signup(UserDto userDto) {


        boolean userExists = userRepo.findByEmail(userDto.getEmail()).isPresent();

        if (userExists) {
            throw new UserExistException(EMAIL_IS_TAKEN);
        }

        User u = User.builder()
                .email(userDto.getEmail())
                .password(bCryptPasswordEncoder.encode(userDto.getPassword()))
                .lastname(userDto.getLastname())
                .firstname(userDto.getFirstname())
                .role("USER")
                .build();

        userRepo.save(u);
        emailSender.sendRegistrationEmail(userDto.getEmail(), Emails.buildRegistrationEmail(userDto.getFirstname()));

        return u;
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

    public String addAdmin( String email){
        User u= findByEmail(email);
        u.setRole("ADMIN");
        userRepo.save(u);
        return u.getFirstname() +" "+u.getLastname()+" ";
    }

    public String removeAdmin( String email){
        User u= findByEmail(email);
        u.setRole("USER");
        userRepo.save(u);

        return u.getFirstname() +" "+u.getLastname();
    }
}
