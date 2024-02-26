package com.example.tarifftracker.infrastructure.adapter.repository;

import com.example.tarifftracker.domain.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
}
