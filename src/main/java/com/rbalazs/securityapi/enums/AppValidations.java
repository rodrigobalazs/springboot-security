package com.rbalazs.securityapi.enums;

import org.springframework.http.HttpStatus;

/**
 * Enum which contains some validation messages related to the Application.
 */
public enum AppValidations {

    USER_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "The user account associated with that email address already exist in the Application"),
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "We couldn´t find a user account associated with that email address"),
    EMPTY_SECRET_KEY(HttpStatus.BAD_REQUEST, "the Authentication Token associated to the User cannot be generated because the secret key 'jwt.token.secret.key' is empty");

    private final HttpStatus httpStatus;
    private final String message;

    AppValidations(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
