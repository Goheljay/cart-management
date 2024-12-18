package com.example.springboot3demo.repository;

import com.example.springboot3demo.modal.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
