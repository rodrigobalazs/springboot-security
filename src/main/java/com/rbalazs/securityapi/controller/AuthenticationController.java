package com.rbalazs.securityapi.controller;

import com.rbalazs.securityapi.dto.AuthenticationDTO;
import com.rbalazs.securityapi.dto.SignUpDTO;
import com.rbalazs.securityapi.model.User;
import com.rbalazs.securityapi.service.security.AuthenticationService;
import com.rbalazs.securityapi.service.security.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.rbalazs.securityapi.enums.AppConstants;

/**
 * Authentication REST Controller.
 */
@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);
    private AuthenticationService authenticationService;
    private UserService userService;

    @Autowired
    public AuthenticationController(final AuthenticationService authenticationService, final UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody SignUpDTO signUpDTO) {
        LOGGER.info("starts to execute authenticationController.signup()");
        User persistedUser = userService.create(signUpDTO.getEmail(), signUpDTO.getPassword(), signUpDTO.getRole());
        return ResponseEntity.ok(AppConstants.NEW_USER_REGISTRATION + signUpDTO.getEmail());
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthenticationDTO authenticationDTO) {
        LOGGER.info("starts to execute authenticationController.login()");
        return authenticationService.login(authenticationDTO.getEmail(), authenticationDTO.getPassword());
    }
}
