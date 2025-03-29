package com.rbalazs.securityapi.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Used to intercept {@link CustomException}.
 */
@ControllerAdvice
public class CustomExceptionHandler {

    /**
     * Intercepts a given {@link CustomException} in order to return to the view an HTTP RESPONSE with the exception
     * message.
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleCustomException(final CustomException ex) {
        return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
    }
}