package com.example.panoptikumstop.controller;

import com.example.panoptikumstop.model.dto.CookieDto;
import com.example.panoptikumstop.services.TrackingSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
/**
 * Die Klasse ApiController ist ein Spring REST-Controller, der Endpunkte für die Verwaltung von Cookies und Tracking-Suchen bereitstellt.
 */
@RestController
@RequestMapping("/panoptikum-stop")
@Slf4j
public class ApiController {


    @Autowired
    private TrackingSearchService trackingSearchService;
    /**
     * Behandelt den GET-Endpunkt zum Suchen eines Cookies anhand seines Namens.
     *
     * @param name Der Name des zu suchenden Cookies.
     * @return Eine ResponseEntity-Instanz mit den gefundenen Cookie-Informationen.
     */
    @GetMapping("/find")
    public ResponseEntity<?> findCookie(@RequestParam("name") String name) {
        log.info("Extern plattform");
        return ResponseEntity.ok(trackingSearchService.findCookie(name));
    }

    /**
     * Behandelt den POST-Endpunkt zum Suchen einer Liste von Cookies.
     *
     * @param list Die Liste der zu suchenden Cookies als JSON-Daten.
     * @return Eine ResponseEntity-Instanz mit den gefundenen Cookie-Informationen.
     */
    @PostMapping("/findAll")
    public ResponseEntity<?> findCookieList(@RequestBody String list) {
        log.info("Extern plattform");
        return ResponseEntity.ok(trackingSearchService.findCookieList(list));
    }
    /**
     * Behandelt den POST-Endpunkt zum Erstellen eines neuen Cookies.
     *
     * @param cookie Die Cookie-Informationen als JSON-Daten.
     * @param auth   Die Authentifizierungsdaten des Benutzers.
     * @return Eine ResponseEntity-Instanz mit einer Erfolgsmeldung.
     */
    @PostMapping("/create")
    public ResponseEntity<?> creatCookie(@RequestBody CookieDto cookie, Authentication auth) {
        log.info("Extern plattform");
        return ResponseEntity.ok(trackingSearchService.createCookie(cookie));
    }
}
