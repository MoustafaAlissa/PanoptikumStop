/*
 * Autoren: Leon Maschlanka, Lukas Maschlanka
 *
 * Dieser Controller nimmt Anfragen zum Registrieren und Einloggen entgegen.
 */
package com.example.panoptikumstop.security;

import com.example.panoptikumstop.model.dto.UserDto;
import com.example.panoptikumstop.services.UserService;
import lombok.AllArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody UserDto userDto) throws BadCredentialsException {

        userService.signIn(userDto);
        return new ResponseEntity<>("Benutzer erfolgreich eingeloggt.", HttpStatus.OK);

    }


    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody UserDto userDto) throws ConstraintViolationException {
        userService.signup(userDto);
        return new ResponseEntity<>("Benutzer erfolgreich registriert.", HttpStatus.OK);
    }

    @PostMapping("/passwordforget")
    public ResponseEntity<String> Passwordforget(@RequestBody  UserDto userDto)
            throws ConstraintViolationException {
        userService.PasswordForget(userDto);

        return new ResponseEntity<>("Email wurde gesendet.",
                HttpStatus.OK);
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
    public String PasswordReset(@ModelAttribute UserDto userDto)
            throws ConstraintViolationException {
        try {
            userService.PasswordReset(userDto);
            return "ResetPasswrod";

        } catch (Exception e) {
            return "Exception";
        }
    }
}
