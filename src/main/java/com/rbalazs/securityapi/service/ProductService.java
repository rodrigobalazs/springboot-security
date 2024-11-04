package com.rbalazs.securityapi.service;

import com.rbalazs.securityapi.model.Product;
import com.rbalazs.securityapi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Product Service.
 *
 * @author Rodrigo Balazs
 */
@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Retrieves a list with all the Products
     *
     * @return a list of {@link Product}
     */
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    /**
     * Save a new Product into the repository.
     *
     * @param product the {@link Product} to save
     * @return a {@link Product} with the persisted product
     */
    public Product save(Product product) {
        productRepository.save(product);
        return product;
    }
}
