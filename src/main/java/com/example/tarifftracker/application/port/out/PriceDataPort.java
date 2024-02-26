package com.example.tarifftracker.application.port.out;

import com.example.tarifftracker.domain.model.Brand;
import com.example.tarifftracker.domain.model.Prices;
import com.example.tarifftracker.domain.model.Product;

import java.time.LocalDateTime;

public interface PriceDataPort {

  Product findProductById(Integer productId);
  Brand findBrandById(Integer brandId);
  Prices findPricesByDateAndBrandAndProduct(Integer brandId, Integer productId, LocalDateTime applicationDate);
}
