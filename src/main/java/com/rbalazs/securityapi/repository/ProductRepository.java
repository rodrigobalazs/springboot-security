package com.rbalazs.securityapi.repository;

import com.rbalazs.securityapi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {}
