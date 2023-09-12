package com.example.panoptikumstop.controller;

import com.example.panoptikumstop.model.dto.CookieDto;
import com.example.panoptikumstop.services.TrackingSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
/**
 * Die Klasse CookieController ist ein Spring-Controller, der Endpunkte für die Verwaltung von Cookies bereitstellt.
 */
@RestController
@RequestMapping("/cookie")
public class CookieController {

    @Autowired
    private TrackingSearchService trackingSearchService;
    /**
     * Behandelt den GET-Endpunkt zum Suchen eines Cookies anhand seines Namens.
     *
     * @param name Der Name des zu suchenden Cookies.
     * @return Eine ResponseEntity-Instanz mit den gefundenen Cookie-Informationen.
     */
    @GetMapping("/find")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<?> findCookie(@RequestParam("name") String name) {
        return ResponseEntity.ok(trackingSearchService.findCookie(name));
    }
    /**
     * Behandelt den POST-Endpunkt zum Suchen einer Liste von Cookies.
     *
     * @param list Die Liste der zu suchenden Cookies als JSON-Daten.
     * @return Eine ResponseEntity-Instanz mit den gefundenen Cookie-Informationen.
     */
    @PostMapping("/findAll")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<?> findCookieList(@RequestBody String list) {

        return ResponseEntity.ok(trackingSearchService.findCookieList(list));
    }
    /**
     * Behandelt den GET-Endpunkt zum Löschen eines Cookies anhand seines Namens.
     *
     * @param name Der Name des zu löschenden Cookies.
     * @return Eine ResponseEntity-Instanz mit einer Erfolgsmeldung.
     */
    @GetMapping("/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity deleteCookie(@RequestParam("name") String name) {
        trackingSearchService.deleteCookie(name);
        return ResponseEntity.ok(name + "  Delete methode wurde aufgerufen !!! ");

    }
    /**
     * Behandelt den POST-Endpunkt zum Erstellen eines neuen Cookies.
     *
     * @param cookie Die Cookie-Informationen als JSON-Daten.
     * @param auth   Die Authentifizierungsdaten des Benutzers.
     * @return Eine ResponseEntity-Instanz mit den erstellten Cookie-Informationen.
     */

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<?> creatCookie(@RequestBody CookieDto cookie, Authentication auth) {
        return ResponseEntity.ok(trackingSearchService.createCookie(cookie));
    }
    /**
     * Behandelt den POST-Endpunkt zum Aktualisieren eines vorhandenen Cookies.
     *
     * @param cookie Die aktualisierten Cookie-Informationen als JSON-Daten.
     * @return Eine ResponseEntity-Instanz mit den aktualisierten Cookie-Informationen.
     */
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateCookie(@RequestBody CookieDto cookie) {
        return ResponseEntity.ok(trackingSearchService.updateCookie(cookie));
    }
    /**
     * Behandelt den GET-Endpunkt zum Abrufen einer Liste von nicht überprüften Tracking-Cookies.
     *
     * @return Eine ResponseEntity-Instanz mit der Liste der nicht überprüften Tracking-Cookies.
     */
    @GetMapping("/uncheckedList")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity getUncheckedList() {
        return ResponseEntity.ok(trackingSearchService.getTrackingCookies());
    }

}









