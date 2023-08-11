/*
 * Autoren: Leon Maschlanka, Lukas Maschlanka
 *
 * Dieser Controller nimmt Anfragen zum Registrieren und Einloggen entgegen.
 */
package com.example.panoptikumstop.controller;

import com.example.panoptikumstop.model.dto.AuthResponse;
import com.example.panoptikumstop.model.dto.LogInDto;
import com.example.panoptikumstop.model.dto.UserDto;
import com.example.panoptikumstop.services.UserService;
import lombok.AllArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> authenticateUser(@Valid @RequestBody LogInDto LogInDto) throws BadCredentialsException {
        return ResponseEntity.ok(userService.signIn(LogInDto));
    }


    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> registerUser(@Valid @RequestBody UserDto userDto) throws ConstraintViolationException {

        return ResponseEntity.ok(userService.signup(userDto));
    }

    @RequestMapping(path = "/passwordforget", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> passwordforget(@RequestBody UserDto userDto) throws ConstraintViolationException {
        userService.PasswordForget(userDto);
        return ResponseEntity.ok(String.format("Email wurde gesendet an %s.", userDto.getEmail()));
    }

    @GetMapping("/confirm")
    public String confirm(@RequestParam("token") String token, @RequestParam("mail") String mail, Model model)
            throws ConstraintViolationException {
        try {
            userService.confirmToken(token);
            UserDto userDto = new UserDto();
            userDto.setEmail(mail);
            model.addAttribute("userDto", userDto);
            return "passwordForget";
        } catch (Exception e) {
            return "Exception";
        }

    }

    @PostMapping("/resetpassword")
    public String passwordReset(@ModelAttribute UserDto userDto)
            throws ConstraintViolationException {
        try {
            userService.PasswordReset(userDto);
            return "ResetPasswrod";

        } catch (Exception e) {
            return "Exception";
        }
    }

    @GetMapping("/verify/{token}")
    public ResponseEntity<?> verifyToken(@PathVariable("token") String token) {

        if (userService.isActive(token)) {
            return ResponseEntity.ok(userService.isActive(token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(userService.isActive(token));
        }

    }
}
