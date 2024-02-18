package com.example.tarifftracker.domain.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.tarifftracker.domain.entity.Prices;

@Repository
public interface PricesRepository extends JpaRepository<Prices, Integer> {

    @Query(value = "SELECT * FROM PRICES WHERE BRAND_ID = ?1 AND PRODUCT_ID = ?2 AND ?3 BETWEEN START_DATE AND END_DATE ORDER BY PRIORITY DESC LIMIT 1", nativeQuery = true)
    Optional<Prices> findPricesByDateAndBrandAndProduct(Integer brandId, Integer productId, LocalDateTime date);
}
