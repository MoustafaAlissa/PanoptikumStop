package com.example.panoptikumstop.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseStatus(HttpStatus.NOT_FOUND)
public class TokenExpiredException extends RuntimeException {

    public TokenExpiredException(String message) {
        super(message);
        log.error(message);
    }
}
