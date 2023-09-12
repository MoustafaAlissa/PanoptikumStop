package com.example.panoptikumstop.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
/**
 * Die Klasse GlobalException ist eine Spring-Komponente, die zur globalen Verarbeitung von Ausnahmen in der Anwendung verwendet wird.
 */
@ControllerAdvice
public class GlobalException {
    /**
     * Behandelt die Ausnahme `UserExistException` und generiert eine Fehlerantwort mit dem HTTP-Statuscode UNAUTHORIZED (401).
     *
     * @param e Die ausgelöste Ausnahme `UserExistException`.
     * @return Eine ResponseEntity-Instanz mit einer Fehlerantwort.
     */

    @ExceptionHandler(UserExistException.class)
    public ResponseEntity UserExistException(UserExistException e) {
        ResponseException responseException = new ResponseException("UserExistException", e.getMessage());
        return new ResponseEntity<>(responseException, HttpStatus.UNAUTHORIZED);
    }
    /**
     * Behandelt die Ausnahme `TokenExpiredException` und generiert eine Fehlerantwort mit dem HTTP-Statuscode BAD_REQUEST (400).
     *
     * @param e Die ausgelöste Ausnahme `TokenExpiredException`.
     * @return Eine ResponseEntity-Instanz mit einer Fehlerantwort.
     */
    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ResponseException> TokenExpiredException(TokenExpiredException e) {
        ResponseException responseException = new ResponseException("TokenExpiredException", e.getMessage());
        return new ResponseEntity<>(responseException, HttpStatus.BAD_REQUEST);
    }
    /**
     * Behandelt die Ausnahme `DuplicatedUserInfoException` und generiert eine Fehlerantwort mit dem HTTP-Statuscode CONFLICT (409).
     *
     * @param e Die ausgelöste Ausnahme `DuplicatedUserInfoException`.
     * @return Eine ResponseEntity-Instanz mit einer Fehlerantwort.
     */
    @ExceptionHandler(DuplicatedUserInfoException.class)
    public ResponseEntity<ResponseException> DuplicatedUserInfoException(DuplicatedUserInfoException e) {
        ResponseException responseException = new ResponseException("DuplicatedUserInfoException", e.getMessage());
        return new ResponseEntity<>(responseException, HttpStatus.CONFLICT);
    }
}
