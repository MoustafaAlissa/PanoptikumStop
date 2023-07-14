package com.example.panoptikumstop.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {


    @ExceptionHandler(UserExistException.class)
    public ResponseEntity UserExistException(UserExistException e) {
        ResponseException responseException = new ResponseException("UserExistException", e.getMessage());
        return new ResponseEntity<>(responseException, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ResponseException> handleTokenExpiredException(TokenExpiredException e) {
        ResponseException responseException = new ResponseException("TokenExpiredException", e.getMessage());
        return new ResponseEntity<>(responseException, HttpStatus.BAD_REQUEST);
    }
}
