package com.example.panoptikumstop.services;

import com.example.panoptikumstop.PanoptikumStopApplication;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;
/**
 * Der AdminService ist ein Spring-Service, der Funktionen zur Verwaltung des Anwendungskontexts bereitstellt.
 * Er ermöglicht das Neustarten der Anwendung zur Laufzeit.
 */

@Service
@AllArgsConstructor
@Slf4j
public class AdminService {
    @Autowired
    private ConfigurableApplicationContext context;
    /**
     * Der Anwendungskontext, der für den Neustart der Anwendung verwendet wird.
     */

    public void restart() {
        Thread thread = new Thread(() -> {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            SpringApplication.exit(context, () -> 0);
            SpringApplication.run(PanoptikumStopApplication.class);
        });

        thread.setDaemon(false);
        log.info("APP start");
        thread.start();
    }

}
