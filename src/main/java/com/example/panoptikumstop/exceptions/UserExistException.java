package com.example.panoptikumstop.exceptions;

public class UserExistException extends RuntimeException {

    public UserExistException(String message)

    {
        super(message);
    }
}
