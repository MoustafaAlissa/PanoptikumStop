package com.example.panoptikumstop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class PanoptikumStopApplication {

    public static void main(String[] args) {
        SpringApplication.run(PanoptikumStopApplication.class, args);
    }

}
