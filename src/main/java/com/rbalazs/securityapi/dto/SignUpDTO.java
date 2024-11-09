package com.rbalazs.securityapi.dto;

import lombok.Data;

/**
 * DTO which represents the user´s details whenever a given User tries to sign-up/register into the app.
 */
@Data
public class SignUpDTO {
    private String email;
    private String password;
    private String role;
}
