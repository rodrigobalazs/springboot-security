package com.rbalazs.securityapi.service.security;

import com.rbalazs.securityapi.enums.AppConstants;
import com.rbalazs.securityapi.enums.AppValidations;
import com.rbalazs.securityapi.exception.CustomException;
import com.rbalazs.securityapi.security.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * Authentication Service
 *
 * @author Rodrigo Balazs
 */
@Service
public class AuthenticationService {

    private AuthenticationManager authenticationManager;
    private JwtTokenUtils jwtTokenUtils;
    private UserService userService;

    @Autowired
    public AuthenticationService(final AuthenticationManager authenticationManager, final JwtTokenUtils jwtTokenUtils,
                                 final UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtils = jwtTokenUtils;
        this.userService = userService;
    }

    /**
     * Login into the Application the User represented by the email address and password given as parameter.
     *
     * @param email the email address to try to login
     * @param password the password
     * @return a JWT Authentication Token in case the login is successful, empty otherwise.
     */
    public String login(final String email, final String password) {

        if (userService.getUserByEmail(email) == null) {
            throw new CustomException(AppValidations.USER_NOT_FOUND);
        }

        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (BadCredentialsException ex) {
            throw new CustomException(AppValidations.WRONG_PASSWORD);
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String role = userDetails.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                // in case no Role it´s retrieved, at least default to CUSTOMER Role
                .orElse(AppConstants.ROLE_CUSTOMER);
        return jwtTokenUtils.generateToken(email, role);
    }
}