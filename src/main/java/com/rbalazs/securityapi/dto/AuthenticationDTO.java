package com.rbalazs.securityapi.dto;

import lombok.Data;

/**
 * DTO which represents the user´s details whenever a given User tries to authenticate/sign-in into the app.
 */
@Data
public class AuthenticationDTO {
    private String email;
    private String password;
}
