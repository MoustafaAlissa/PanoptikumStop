package com.example.panoptikumstop.controller;


import com.example.panoptikumstop.exceptions.TokenExpiredException;
import com.example.panoptikumstop.services.AdminService;
import com.example.panoptikumstop.services.UserService;
import com.example.panoptikumstop.services.config.PauseInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/Admin")

public class AdminController {

    private final PauseInterceptor pauseInterceptor;
    private UserService userService;
    private AdminService adminService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity addAdmin(@RequestParam("email") String email) {

        return ResponseEntity.ok(userService.addAdmin(email) + "Der User: " + email + " ist jetzt Admin.");
    }

    @PostMapping("/remove")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity remove(@RequestParam("email") String email) {

        return ResponseEntity.ok(userService.removeAdmin(email) + "jetzt ein User");
    }

    @GetMapping(path = "/break/{time}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String pauseApp(@PathVariable long time) throws InterruptedException {
        pauseInterceptor.setPaused(true);
        Thread.sleep(( time / (60 * 1000)));
        pauseInterceptor.setPaused(false);
        return "App is now active again";
    }

    @PostMapping("/restart")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void restart() {
        adminService.restart();


    }


}




