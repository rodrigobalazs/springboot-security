package com.rbalazs.securityapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.Validate;

/**
 * Represents a given User Role.
 */
@Entity
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    /** Empty Constructor required by JPA / Hibernate. */
    public Role() {}

    /**
     * Creates a new Role.
     */
    public Role(final String theName) {
        Validate.notEmpty(theName, "The role name cannot be null nor empty");
        name = theName;
    }

}