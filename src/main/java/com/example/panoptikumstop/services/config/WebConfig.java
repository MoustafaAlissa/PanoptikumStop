package com.example.panoptikumstop.services.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 * Die {@code WebConfig}-Klasse ist eine Konfigurationsklasse, die benutzerdefinierte Interceptor für Spring MVC konfiguriert.
 * Interceptors können verwendet werden, um Anfragen und Antworten während des Verarbeitungsprozesses zu überwachen und zu beeinflussen.
 * In diesem Fall wird der {@code PauseInterceptor} hinzugefügt, um bestimmte Anfragen zu pausieren und zu steuern.
 * Diese Klasse implementiert das {@code WebMvcConfigurer}-Interface, um die Interceptors zu konfigurieren.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private PauseInterceptor pauseInterceptor;
    /**
     * Konfiguriert die Interceptors für Spring MVC.
     *
     * @param registry Das {@code InterceptorRegistry}-Objekt, das verwendet wird, um Interceptors hinzuzufügen.
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(pauseInterceptor);
    }
}
