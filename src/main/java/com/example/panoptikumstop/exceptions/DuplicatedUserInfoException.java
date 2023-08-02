package com.example.panoptikumstop.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
@Slf4j
public class DuplicatedUserInfoException extends RuntimeException {

    public DuplicatedUserInfoException(String message) {
        super(message);
        log.error(message);
    }

}
