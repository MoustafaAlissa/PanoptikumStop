package com.example.panoptikumstop.controller;

import com.example.panoptikumstop.model.dto.CookieDto;
import com.example.panoptikumstop.services.TrackingSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/panoptikum-stop")
@Slf4j
public class ApiController {


    @Autowired
    private TrackingSearchService trackingSearchService;

    @GetMapping("/find")
    public ResponseEntity<?> findCookie(@RequestParam("name") String name) {
        log.info("Extern plattform");
        return ResponseEntity.ok(trackingSearchService.findCookie(name));
    }


    @PostMapping("/findAll")
    public ResponseEntity<?> findCookieList(@RequestBody String list) {
        log.info("Extern plattform");
        return ResponseEntity.ok(trackingSearchService.findCookieList(list));
    }

    @PostMapping("/create")
    public ResponseEntity<?> creatCookie(@RequestBody CookieDto cookie, Authentication auth) {
        log.info("Extern plattform");
        return ResponseEntity.ok(trackingSearchService.createCookie(cookie));
    }
}
