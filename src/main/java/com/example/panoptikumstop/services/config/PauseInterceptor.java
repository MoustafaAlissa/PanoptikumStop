package com.example.panoptikumstop.services.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Die {@code PauseInterceptor}-Klasse ist ein Spring-Interceptor, der Anfragen während ihres Verarbeitungsprozesses abfängt und steuert.
 * Sie ermöglicht es, den Serverzustand dynamisch zu steuern, indem sie Anfragen pausiert, wenn ein bestimmter Zustand aktiviert ist.
 * Diese Klasse implementiert das {@code HandlerInterceptor}-Interface von Spring MVC.
 */
@Component
@Service
@Slf4j
public class PauseInterceptor implements HandlerInterceptor {

    private boolean isPaused = false;
    /**
     * Diese Methode wird vor der eigentlichen Verarbeitung einer Anfrage aufgerufen.
     * Sie prüft, ob der Server im pausierten Zustand ist, und passt das Verhalten entsprechend an.
     *
     * @param request  Das die Anfrage darstellt.
     * @param response Das die Antwort darstellt.
     * @param handler  Das Objekt, das den verarbeitenden Handler darstellt.
     * @return {@code true}, wenn die Anfrage fortgesetzt werden soll, andernfalls false
     * @throws Exception Eine Ausnahme, die während der Verarbeitung auftreten kann.
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (isPaused) {
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            log.info("Server wurde Pausiert.");
            response.getWriter().write("App is currently paused. Try again later");
            return false;
        }
        return true;
    }
    /**
     * Setzt den Pausenzustand des Servers.
     *
     * @param paused {@code true}, wenn der Server pausiert werden soll, andernfalls {@code false}.
     */
    public void setPaused(boolean paused) {
        isPaused = paused;
    }
}
