package com.rbalazs.securityapi.dto;

import lombok.Data;

@Data
public class SignUpDTO {
    private String email;
    private String password;
    private String role;
}
