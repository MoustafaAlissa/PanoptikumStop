package com.example.panoptikumstop.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 * In dieser Konfiguration wird definiert, dass Anfragen von allen Ursprüngen (Origins) erlaubt sind,
 * alle HTTP-Methoden und Header erlaubt sind.
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    /**
     * Erstellt und konfiguriert einen WebMvcConfigurer, der CORS für die Anwendung aktiviert.
     *
     * @return Der konfigurierte WebMvcConfigurer für CORS.
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("*")
                        .allowedHeaders("*");
            }
        };
    }
}