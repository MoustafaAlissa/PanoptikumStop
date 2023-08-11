package com.example.panoptikumstop.controller;

import com.example.panoptikumstop.model.dto.DominDto;
import com.example.panoptikumstop.services.DominService;
import com.example.panoptikumstop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/domin")
public class DominController {
    @Autowired
    private DominService dominService;
    @Autowired
    private UserService userService;

    @PostMapping("/spenden")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<?> Datenspende(@RequestBody DominDto dominDto, @RequestHeader("Authorization") String token) {

        if (token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(7);
            String userEmail = userService.getEmail(jwtToken);
            dominService.Datenspende(dominDto);
            userService.spenden(userEmail);

        }
        return ResponseEntity.ok("Danke für spenden");
    }

    @GetMapping("/get/{domin}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<?> domin(@PathVariable String domin) {
        return ResponseEntity.ok(dominService.findByDomin(domin));

    }


}
