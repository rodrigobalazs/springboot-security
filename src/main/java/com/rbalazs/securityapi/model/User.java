package com.rbalazs.securityapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.Validate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * Represents a given User, a User could be associated to a specific {@link Role} at most.
 * For this specific app, UserDetails.username will be represented by 'email'
 *
 * @author Rodrigo Balazs
 */
@Entity
@Getter
@Setter
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;

    @ManyToOne
    private Role role;

    /** Empty Constructor required by JPA / Hibernate. */
    public User() {}

    /**
     * Creates a new User.
     *
     * @param theEmail the user email address
     * @param thePassword the user password
     * @param theRole the assigned role
     */
    public User(final String theEmail, final String thePassword, final Role theRole) {
        Validate.notEmpty(theEmail, "The user email cannot be null nor empty");
        Validate.notEmpty(thePassword, "The user password cannot be null nor empty");
        Validate.notNull(theRole, "The user role cannot be null");

        email = theEmail;
        password = thePassword;
        role = theRole;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.getName()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}