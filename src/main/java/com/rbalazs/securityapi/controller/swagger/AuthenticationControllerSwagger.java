package com.rbalazs.securityapi.controller.swagger;

import com.rbalazs.securityapi.controller.AuthenticationController;
import com.rbalazs.securityapi.dto.AuthenticationDTO;
import com.rbalazs.securityapi.dto.SignUpDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Swagger interface related to {@link AuthenticationController}.
 * API Documentation/Swagger at => http://<project_url>/swagger-ui/index.html
 */
@Tag(name = "Authentication API", description = "API endpoints related to User´s Authentication")
public interface AuthenticationControllerSwagger {

    @Operation(summary = "Logins a given User. The user´s details (email,password) could be taken from AuthenticationDTO. " +
                         "In case the User logins successfully, this operation returns an Authentication Token associated to the logged-in User")
    public String login(@RequestBody AuthenticationDTO authenticationDTO);

    @Operation(summary = "Sign-up / Register a given User. The user´s details (email,password,role) could be taken from SignUpDTO")
    public ResponseEntity<String> signUp(@RequestBody SignUpDTO signUpDTO);
}
