package com.example.panoptikumstop.controller;

import com.example.panoptikumstop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * Die Klasse UserController ist ein Spring-Controller, es ist für Test gedacht.
 */
@RestController
@RequestMapping(path = "/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/email")
    public String getUserEmail(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(7);
            System.out.println(token);
            String userEmail = userService.getEmail(jwtToken);
            return userEmail;
        }
        return "Invalid token";
    }
}
