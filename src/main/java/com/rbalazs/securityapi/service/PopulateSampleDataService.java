package com.rbalazs.securityapi.service;

import com.rbalazs.securityapi.enums.AppConstants;
import com.rbalazs.securityapi.model.Product;
import com.rbalazs.securityapi.model.Role;
import com.rbalazs.securityapi.repository.ProductRepository;
import com.rbalazs.securityapi.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;

/**
 * Populates the database with sample data related to Roles and Products in case those tables are empty.
 */
@Service
public class PopulateSampleDataService implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(PopulateSampleDataService.class);

    ProductRepository productRepository;
    RoleRepository roleRepository;

    @Autowired
    public PopulateSampleDataService(final ProductRepository productRepository, final RoleRepository roleRepository) {
        this.productRepository = productRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if ( CollectionUtils.isNotEmpty(roleRepository.findAll())
                || CollectionUtils.isNotEmpty(productRepository.findAll())){
            return;
        }

        LOGGER.info("populates the database with some initial sample data for Roles and Products ..");
        createProduct(AppConstants.PRODUCT_OFFICE_CHAIR_NAME, AppConstants.PRODUCT_OFFICE_CHAIR_AVAILABLE_QUANTITY);
        createProduct(AppConstants.PRODUCT_SOFA_NAME, AppConstants.PRODUCT_SOFA_AVAILABLE_QUANTITY);

        createRole(AppConstants.ROLE_ADMIN);
        createRole(AppConstants.ROLE_CUSTOMER);
    }

    private void createProduct(final String productName, final Integer availableQuantity){
        Product product = new Product(productName, availableQuantity);
        productRepository.save(product);
    }

    private Role createRole(final String roleName){
        Role role = new Role(roleName);
        return roleRepository.save(role);
    }
}