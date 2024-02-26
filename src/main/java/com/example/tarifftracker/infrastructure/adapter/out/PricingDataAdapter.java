package com.example.tarifftracker.infrastructure.adapter.out;

import com.example.tarifftracker.application.port.out.PriceDataPort;
import com.example.tarifftracker.domain.exception.DatabaseNotFoundException;
import com.example.tarifftracker.domain.model.Brand;
import com.example.tarifftracker.domain.model.Prices;
import com.example.tarifftracker.domain.model.Product;
import com.example.tarifftracker.infrastructure.adapter.repository.BrandRepository;
import com.example.tarifftracker.infrastructure.adapter.repository.PricesRepository;
import com.example.tarifftracker.infrastructure.adapter.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.example.tarifftracker.shared.constants.Constants.*;
import static java.lang.String.format;

@Component
@RequiredArgsConstructor
public class PricingDataAdapter implements PriceDataPort {

  private final PricesRepository pricesRepository;
  private final ProductRepository productRepository;
  private final BrandRepository brandRepository;

  @Override
  public Product findProductById(Integer productId) {
    return productRepository.findById(productId)
                .orElseThrow(() -> new DatabaseNotFoundException(format(NOT_FOUND_WITH_ID_MESSAGE, PRODUCT_ENTITY_NAME, productId)));
  }

  @Override
  public Brand findBrandById(Integer brandId) {
        return brandRepository.findById(brandId)
                .orElseThrow(() -> new DatabaseNotFoundException(format(NOT_FOUND_WITH_ID_MESSAGE, BRAND_ENTITY_NAME, brandId)));
  }

  @Override
  public Prices findPricesByDateAndBrandAndProduct(Integer brandId, Integer productId, LocalDateTime applicationDate) {
    return pricesRepository.findPricesByDateAndBrandAndProduct(brandId, productId, applicationDate)
                .orElseThrow(() -> new DatabaseNotFoundException(format(NOT_FOUND_WITH_PARAMETERS_MESSAGE, PRICES_ENTITY_NAME,
                                format("Product Id = %s, Brand Id = %s, Application Date %s", productId, brandId, applicationDate))));
  }
}
