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
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
/**
 * Die Klasse AuthController ist ein Spring-Controller, der Endpunkte für die Benutzerauthentifizierung und -registrierung bereitstellt.
 */

@Controller
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    /**
     * Behandelt den POST-Endpunkt zum Authentifizieren eines Benutzers.
     *
     * @param LogInDto Die Anmeldeinformationen des Benutzers.
     * @return Eine ResponseEntity-Instanz mit dem Authentifizierungsergebnis.
     * @throws BadCredentialsException Wenn die Anmeldeinformationen ungültig sind.
     */
    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> authenticateUser(@Valid @RequestBody LogInDto LogInDto) throws BadCredentialsException {
        return ResponseEntity.ok(userService.signIn(LogInDto));
    }
    /**
     * Behandelt den POST-Endpunkt zur Registrierung eines neuen Benutzers.
     *
     * @param userDto Die Benutzerinformationen für die Registrierung.
     * @return Eine ResponseEntity-Instanz mit dem Registrierungsergebnis.
     * @throws ConstraintViolationException Wenn die Registrierungseingaben ungültig sind.
     */

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> registerUser(@Valid @RequestBody UserDto userDto) {

        return ResponseEntity.ok(userService.signup(userDto));
    }

    /**
     * Behandelt den POST-Endpunkt zur Passwordforget eines Benutzers.
     * @param userDto Die Benutzerinformationen für die Password forget.
     * @returnEine ResponseEntity-Instanz mit dem Passwordforget.
     * @throws ConstraintViolationException Wenn die email ungültig sind.
     */
    @RequestMapping(path = "/passwordforget", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> passwordforget(@RequestBody UserDto userDto) {
        userService.PasswordForget(userDto);
        return ResponseEntity.ok(String.format("Email wurde gesendet an %s.", userDto.getEmail()));
    }

    @GetMapping("/confirm")
    public String confirm(@RequestParam("token") String token, @RequestParam("mail") String mail, Model model)
            {
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
          {
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
