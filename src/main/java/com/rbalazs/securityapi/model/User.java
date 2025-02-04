package com.rbalazs.securityapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.Validate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;
import java.util.Collections;

/**
 * Represents a given User, a User could be associated to a specific {@link Role} at most.
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

    /** user´s email, represents spring security 'UserDetails.username' */
    private String email;

    /** user´s password, the password will be stored encrypted/hashed via spring security {@link BCryptPasswordEncoder} */
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
        Validate.notEmpty(theEmail, "The user´s email cannot be null nor empty");
        Validate.notEmpty(thePassword, "The user´s password cannot be null nor empty");
        Validate.notNull(theRole, "The user´s role cannot be null");

        email = theEmail;
        password = thePassword;
        role = theRole;
    }

    /**
     * Retrieves the User´s Role ( a.k.a Authority ), this is an spring security method.
     */
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