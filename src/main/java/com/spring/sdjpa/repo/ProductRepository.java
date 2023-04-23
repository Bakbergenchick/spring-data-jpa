package com.spring.sdjpa.repo;

import com.spring.sdjpa.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByDescription(String description);

}
