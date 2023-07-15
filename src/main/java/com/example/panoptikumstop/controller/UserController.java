package com.example.panoptikumstop.controller;


import com.example.panoptikumstop.model.dto.CookieDto;
import com.example.panoptikumstop.model.dto.UserDto;
import com.example.panoptikumstop.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/Admin")

public class UserController {

private   UserService userService;

@PostMapping("/add")
@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity addAdmin(@RequestParam("email") String email){

            return ResponseEntity.ok(userService.addAdmin(email)+"Der User: "+email+" ist jetzt Admin.");
    }
    @PostMapping("/remove")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity remove(@RequestParam("email") String email){

        return ResponseEntity.ok(userService.removeAdmin(email)+"jetzt ein User");
    }

}



