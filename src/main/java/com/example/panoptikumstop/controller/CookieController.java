package com.example.panoptikumstop.controller;

import com.example.panoptikumstop.model.dto.CookieDto;
import com.example.panoptikumstop.services.TrackingSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cookie")
public class CookieController {

    @Autowired
    private TrackingSearchService trackingSearchService;

    @GetMapping("/find")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<?> findCookie(@RequestParam("name") String name) {
        return ResponseEntity.ok(trackingSearchService.findCookie(name));
    }

    @PostMapping("/findAll")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<?> findCookieList(@RequestBody String list) {

        return ResponseEntity.ok(trackingSearchService.findCookieList(list));
    }

    @GetMapping("/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity deleteCookie(@RequestParam("name") String name) {
        trackingSearchService.deleteCookie(name);
        return ResponseEntity.ok(name + "  Delete methode wurde aufgerufen !!! ");

    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<?> creatCookie(@RequestBody CookieDto cookie, Authentication auth) {
        return ResponseEntity.ok(trackingSearchService.createCookie(cookie));
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateCookie(@RequestBody CookieDto cookie) {
        return ResponseEntity.ok(trackingSearchService.updateCookie(cookie));
    }

    @GetMapping("/uncheckedList")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity getUncheckedList() {
        return ResponseEntity.ok(trackingSearchService.getUncheckedList());
    }

}









