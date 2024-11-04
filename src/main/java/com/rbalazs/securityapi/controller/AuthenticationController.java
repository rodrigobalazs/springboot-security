package com.rbalazs.securityapi.controller;

import com.rbalazs.securityapi.dto.AuthenticationRequest;
import com.rbalazs.securityapi.enums.AppConstants;
import com.rbalazs.securityapi.security.JwtTokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);
    private AuthenticationManager authenticationManager;
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenUtils jwtTokenUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtils = jwtTokenUtils;
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthenticationRequest authRequest) {
        LOGGER.info("starts to execute authenticationController.login()");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userRole = userDetails.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                // in case no Role it´s retrieved, at least default to CUSTOMER Role
                .orElse(AppConstants.ROLE_CUSTOMER);
        return jwtTokenUtils.generateToken(authRequest.getEmail(), userRole);
    }
}
