package com.example.tarifftracker.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tarifftracker.domain.entity.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
}
