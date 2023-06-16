package com.example.panoptikumstop.controller;

import com.example.panoptikumstop.model.dto.CookieDto;
import com.example.panoptikumstop.security.MyUserDetails;
import com.example.panoptikumstop.services.TrackingSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cookie")
public class CookieController {

    @Autowired
    private TrackingSearchService trackingSearchService;

    @GetMapping("/find")
    public ResponseEntity<?> findCookie(@RequestParam("name") String name) {
        return ResponseEntity.ok(trackingSearchService.findCookie(name));
    }

    @GetMapping("/delete")
    public ResponseEntity deleteCookie(@RequestParam("name") String name) {
        trackingSearchService.deleteCookie(name);
        return ResponseEntity.ok(name + "  Delete methode wurde aufgerufen !!! ");

    }

    @PostMapping("/create")
    public ResponseEntity<?> creatCookie(@RequestBody CookieDto cookie, Authentication auth) {
        return ResponseEntity.ok(trackingSearchService.createCookie(cookie));
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateCookie(@RequestBody CookieDto cookie) {
        return ResponseEntity.ok(trackingSearchService.updateCookie(cookie));
    }

    @GetMapping("/uncheckedList")
    public ResponseEntity getUncheckedList() {

        return ResponseEntity.ok(trackingSearchService.getUncheckedList());

    }

    private MyUserDetails getCurrentUserDetails(Authentication auth) {
        return (MyUserDetails) auth.getPrincipal();
    }
}









