package com.example.panoptikumstop.services;

import static org.junit.jupiter.api.Assertions.*;

import com.example.panoptikumstop.exceptions.DuplicatedUserInfoException;
import com.example.panoptikumstop.exceptions.TokenExpiredException;
import com.example.panoptikumstop.exceptions.UserExistException;
import com.example.panoptikumstop.model.dto.AuthResponse;
import com.example.panoptikumstop.model.dto.LogInDto;
import com.example.panoptikumstop.model.dto.UserDto;
import com.example.panoptikumstop.model.entity.User;
import com.example.panoptikumstop.repo.UserRepo;
import com.example.panoptikumstop.security.email.EmailSender;
import com.example.panoptikumstop.security.jwt.JwtUtils;
import com.example.panoptikumstop.security.token.ConfirmationToken;
import com.example.panoptikumstop.security.token.ConfirmationTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepo userRepo;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private ConfirmationTokenService confirmationTokenService;

    @Mock
    private EmailSender emailSender;

    @Mock
    private JwtUtils jwtUtils;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(new BCryptPasswordEncoder(), emailSender, jwtUtils, userRepo, authenticationManager, confirmationTokenService);
    }

    @Test
    void testSignIn_Successful() {
        LogInDto logInDto = new LogInDto();
        logInDto.setEmail("test@example.com");
        logInDto.setPassword("password");

        User user = new User();
        user.setEmail(logInDto.getEmail());
        user.setPassword("hashedPassword");

        when(userRepo.findByEmail(logInDto.getEmail())).thenReturn(Optional.of(user));
        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(jwtUtils.generateJwtToken(logInDto.getEmail())).thenReturn("jwtToken");

        AuthResponse authResponse = userService.signIn(logInDto);

        assertNotNull(authResponse);
        assertEquals("jwtToken", authResponse.token());
    }

    @Test
    void testSignIn_InvalidCredentials() {
        LogInDto logInDto = new LogInDto();
        logInDto.setEmail("test@example.com");
        logInDto.setPassword("invalidPassword");

        User user = new User();
        user.setEmail(logInDto.getEmail());
        user.setPassword("hashedPassword");

        when(userRepo.findByEmail(logInDto.getEmail())).thenReturn(Optional.of(user));
        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("Invalid credentials"));

        assertThrows(UserExistException.class, () -> userService.signIn(logInDto));
    }

    @Test
    void testSignup_Successful() {
        UserDto userDto = new UserDto();
        userDto.setEmail("test@example.com");
        userDto.setPassword("password");
        userDto.setFirstname("John");
        userDto.setLastname("Doe");

        when(userRepo.existsByEmail(userDto.getEmail())).thenReturn(false);
        when(userRepo.save(any())).thenReturn(new User());
        when(jwtUtils.generateJwtToken(userDto.getEmail())).thenReturn("jwtToken");

        AuthResponse authResponse = userService.signup(userDto);

        assertNotNull(authResponse);
        assertEquals("jwtToken",authResponse.token());
    }

    @Test
    void testSignup_DuplicatedEmail() {
        UserDto userDto = new UserDto();
        userDto.setEmail("test@example.com");
        userDto.setPassword("password");
        userDto.setFirstname("John");
        userDto.setLastname("Doe");

        when(userRepo.existsByEmail(userDto.getEmail())).thenReturn(true);

        assertThrows(DuplicatedUserInfoException.class, () -> userService.signup(userDto));
    }

    @Test
    void testPasswordForget() {
        UserDto userDto = new UserDto();
        userDto.setEmail("test@example.com");

        User user = new User();
        user.setEmail(userDto.getEmail());

        when(userRepo.findByEmail(userDto.getEmail())).thenReturn(Optional.of(user));


        assertDoesNotThrow(() -> userService.PasswordForget(userDto));
    }

    @Test
    void testPasswordForget_UserNotExist() {
        UserDto userDto = new UserDto();
        userDto.setEmail("test@example.com");

        when(userRepo.findByEmail(userDto.getEmail())).thenReturn(Optional.empty());

        assertThrows(UserExistException.class, () -> userService.PasswordForget(userDto));
    }

    @Test
    void testPasswordReset() {
        UserDto userDto = new UserDto();
        userDto.setEmail("test@example.com");
        userDto.setPassword("newPassword");

        User user = new User();
        user.setEmail(userDto.getEmail());

        when(userRepo.findByEmail(userDto.getEmail())).thenReturn(Optional.of(user));
        when(userRepo.save(any())).thenReturn(new User());


        assertDoesNotThrow(() -> userService.PasswordReset(userDto));
    }

    @Test
    void testConfirmToken() {
        ConfirmationToken token = new ConfirmationToken(UUID.randomUUID().toString(), LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), new User());

        when(confirmationTokenService.getToken(token.getToken())).thenReturn(Optional.of(token));

        assertDoesNotThrow(() -> userService.confirmToken(token.getToken()));
    }

    @Test
    void testConfirmToken_ExpiredToken() {
        ConfirmationToken token = new ConfirmationToken(UUID.randomUUID().toString(), LocalDateTime.now().minusMinutes(30), LocalDateTime.now().minusMinutes(15), new User());

        when(confirmationTokenService.getToken(token.getToken())).thenReturn(Optional.of(token));

        assertThrows(TokenExpiredException.class, () -> userService.confirmToken(token.getToken()));
    }
}
