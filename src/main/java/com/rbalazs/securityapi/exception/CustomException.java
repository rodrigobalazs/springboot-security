package com.rbalazs.securityapi.exception;

import com.rbalazs.securityapi.enums.AppValidations;
import org.springframework.http.HttpStatus;

/**
 * Custom Exception related to the App. The error messages/status codes could be taken from {@link AppValidations}
 */
public class CustomException extends RuntimeException {
    private HttpStatus status;
    private String message;

    public CustomException(final AppValidations appValidations) {
        this.status = appValidations.getHttpStatus();
        this.message = appValidations.getMessage();
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}