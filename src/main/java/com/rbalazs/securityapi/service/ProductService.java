package com.rbalazs.securityapi.service;

import com.rbalazs.securityapi.model.Product;
import com.rbalazs.securityapi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Product Service.
 */
@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product save(Product product) {
        productRepository.save(product);
        return product;
    }
}
