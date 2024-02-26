package com.example.tarifftracker.infrastructure.adapter.repository;

import com.example.tarifftracker.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
