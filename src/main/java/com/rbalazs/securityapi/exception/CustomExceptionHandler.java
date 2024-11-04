package com.rbalazs.securityapi.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Used to intercept {@link CustomException}
 *
 * @author Rodrigo Balazs
 */
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Intercepts a given {@link CustomException} in order to return to the view an HTTP RESPONSE with the exception
     * message.
     *
     * @return an HTTP RESPONSE with a given HTTP Status and a JSON body with the exception message e.g =>
     * {the User was not found in the Application}
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleCustomException(final CustomException ex, final WebRequest request) {
        return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
    }
}