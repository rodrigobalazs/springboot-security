package com.rbalazs.securityapi.controller;

import com.rbalazs.securityapi.controller.swagger.ProductControllerSwagger;
import com.rbalazs.securityapi.model.Product;
import com.rbalazs.securityapi.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Product REST Controller.
 * API Documentation/Swagger at => http://<project_url>/swagger-ui/index.html
 */
@RestController
@RequestMapping("/products")
public class ProductController implements ProductControllerSwagger {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;

    @Autowired
    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/getProducts")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public ResponseEntity<List<Product>> getProducts() {
        LOGGER.info("starts to execute productController.getProducts()");
        List<Product> products = productService.getProducts();
        return ResponseEntity.ok(products);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Product saveProduct(@RequestBody Product product) {
        LOGGER.info("starts to execute productController.saveProduct()");
        return productService.save(product);
    }
}