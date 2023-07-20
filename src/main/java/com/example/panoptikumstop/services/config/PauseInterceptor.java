package com.example.panoptikumstop.services.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Component
@Service
@Slf4j
public class PauseInterceptor implements HandlerInterceptor {

    private boolean isPaused = false;

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

    public void setPaused(boolean paused) {
        isPaused = paused;
    }
}
