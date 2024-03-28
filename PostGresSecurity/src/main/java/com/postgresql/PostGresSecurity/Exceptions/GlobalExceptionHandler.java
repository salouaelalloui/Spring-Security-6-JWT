package com.postgresql.PostGresSecurity.Exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = LoginException.class)
    public ResponseEntity<ErrorResponse> handleLoginException(LoginException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),

                HttpStatus.UNAUTHORIZED,
                ZonedDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = TokenException.class)
    public ResponseEntity<ErrorResponse> handleTokenException(TokenException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),

                HttpStatus.INTERNAL_SERVER_ERROR,
                ZonedDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
