package com.rbalazs.securityapi.enums;

import org.springframework.http.HttpStatus;

/**
 * Enum which contains some validation messages related to the Application.
 */
public enum AppValidations {

    USER_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "The User Account associated with that email address already exist in the Application"),
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "couldn´t find a User Account associated with that email address"),
    EMPTY_SECRET_KEY(HttpStatus.BAD_REQUEST, "the Authentication Token associated to the User Account cannot be generated because the secret key 'jwt.auth.token.secret.key' is empty"),
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST, "Incorrect Password, please try again");

    private final HttpStatus httpStatus;
    private final String message;

    AppValidations(final HttpStatus httpStatus, final String message) {
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
