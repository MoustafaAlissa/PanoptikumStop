package com.example.panoptikumstop.controller;


import com.example.panoptikumstop.services.AdminService;
import com.example.panoptikumstop.services.UserService;
import com.example.panoptikumstop.services.config.PauseInterceptor;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
/**
 * Die Klasse AdminController ist ein Spring REST-Controller, der Endpunkte für administrative Aufgaben bereitstellt.
 */
@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping(path = "/api/Admin")

public class AdminController {

    private final PauseInterceptor pauseInterceptor;
    private UserService userService;
    private AdminService adminService;
    /**
     * Behandelt den POST-Endpunkt zum Hinzufügen eines Administrators.
     * Benötigt die Berechtigung 'ADMIN'.
     *
     * @param email Die E-Mail-Adresse des Benutzers, der zum Administrator gemacht werden soll.
     * @return Eine ResponseEntity-Instanz mit einer Erfolgsmeldung.
     */
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity addAdmin(@RequestParam("email") String email) {

        return ResponseEntity.ok(userService.addAdmin(email) + "Der User: " + email + " ist jetzt Admin.");
    }
    /**
     * Behandelt den POST-Endpunkt zum Entfernen eines Administrators.
     * Benötigt die Berechtigung 'ADMIN'.
     *
     * @param email Die E-Mail-Adresse des Administrators, der zu einem normalen Benutzer gemacht werden soll.
     * @return Eine ResponseEntity-Instanz mit einer Erfolgsmeldung.
     */
    @PostMapping("/remove")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity remove(@RequestParam("email") String email) {

        return ResponseEntity.ok(userService.removeAdmin(email) + "jetzt ein User");
    }
    /**
     * Behandelt den GET-Endpunkt zum Pausieren der Anwendung für eine bestimmte Zeit.
     * Benötigt die Berechtigung 'ADMIN'.
     *
     * @param time Die Dauer der Pause in Minuten.
     * @return Eine Meldung, die den Pausevorgang bestätigt.
     * @throws InterruptedException Wenn das Pausieren des Threads unterbrochen wird.
     */

    @GetMapping(path = "/break/{time}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String pauseApp(@PathVariable long time) throws InterruptedException {


        pauseInterceptor.setPaused(true);

        Thread.sleep(time * 60000);
        log.info("Server wurde pausiert um: " + time + " Minuten");
        pauseInterceptor.setPaused(false);
        return "App is now active again";
    }
    /**
     * Behandelt den POST-Endpunkt zum Neustarten der Anwendung.
     * Benötigt die Berechtigung 'ADMIN'.
     */
    @PostMapping("/restart")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void restart() {
        adminService.restart();


    }


}




