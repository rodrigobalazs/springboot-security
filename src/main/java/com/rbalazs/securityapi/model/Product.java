package com.rbalazs.securityapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.Validate;

/**
 * Represents a given Product.
 */
@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int availableQuantity;

    /** Empty Constructor required by JPA / Hibernate. */
    public Product() {}

    /**
     * Creates a new Product.
     *
     * @param theName the product name
     * @param theAvailableQuantity the product available quantity
     */
    public Product(final String theName, final int theAvailableQuantity) {
        Validate.notEmpty(theName, "The product name cannot be null nor empty");
        Validate.isTrue(theAvailableQuantity >= 0, "The available quantity must be greater or equal to zero");
        name = theName;
        availableQuantity = theAvailableQuantity;
    }
}