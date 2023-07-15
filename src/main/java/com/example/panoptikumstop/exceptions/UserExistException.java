package com.example.panoptikumstop.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserExistException extends RuntimeException {
    public UserExistException(String message) {
        super(message);
      log.error(message);
    }
}
