package com.example.tarifftracker.domain.service;


import static com.example.tarifftracker.shared.constants.Constants.BRAND_ENTITY_NAME;
import static com.example.tarifftracker.shared.constants.Constants.NOT_FOUND_WITH_ID_MESSAGE;
import static com.example.tarifftracker.shared.constants.Constants.NOT_FOUND_WITH_PARAMETERS_MESSAGE;
import static com.example.tarifftracker.shared.constants.Constants.PRICES_ENTITY_NAME;
import static com.example.tarifftracker.shared.constants.Constants.PRODUCT_ENTITY_NAME;
import static com.example.tarifftracker.shared.util.DateTimeUtil.convertStringToLocalDateTime;
import static java.lang.Integer.valueOf;
import static java.lang.String.format;

import org.springframework.stereotype.Service;

import com.example.tarifftracker.application.dto.PriceDetailsDto;
import com.example.tarifftracker.application.dto.PriceQueryDto;
import com.example.tarifftracker.application.mapper.PriceMapper;
import com.example.tarifftracker.application.service.PricingService;
import com.example.tarifftracker.domain.exception.DatabaseNotFoundException;
import com.example.tarifftracker.domain.repository.BrandRepository;
import com.example.tarifftracker.domain.repository.PricesRepository;
import com.example.tarifftracker.domain.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PricingServiceImpl implements PricingService {

    private final PricesRepository pricesRepository;
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final PriceMapper priceMapper;

    @Override
    public PriceDetailsDto calculatePriceDetails(PriceQueryDto priceQueryDto) {

        log.info("Retrieving product with id: {}", priceQueryDto.getProductId());
        var product = productRepository.findById(valueOf(priceQueryDto.getProductId()))
                .orElseThrow(() -> new DatabaseNotFoundException(format(NOT_FOUND_WITH_ID_MESSAGE, PRODUCT_ENTITY_NAME, priceQueryDto.getProductId())));

        log.info("Retrieving brand with id: {}", priceQueryDto.getBrandId());
        var brand = brandRepository.findById(valueOf(priceQueryDto.getBrandId()))
                .orElseThrow(() -> new DatabaseNotFoundException(format(NOT_FOUND_WITH_ID_MESSAGE, BRAND_ENTITY_NAME, priceQueryDto.getBrandId())));
        var applicationDate = convertStringToLocalDateTime(priceQueryDto.getApplicationDate());

        log.info("Calculating prices!");
        var prices = pricesRepository.findPricesByDateAndBrandAndProduct(brand.getBrandId(), product.getProductId(), applicationDate)
                .orElseThrow(() -> new DatabaseNotFoundException(format(NOT_FOUND_WITH_PARAMETERS_MESSAGE, PRICES_ENTITY_NAME,
                                format("Product Id = %s, Brand Id = %s, Application Date %s", priceQueryDto.getProductId(), priceQueryDto.getBrandId(), priceQueryDto.getApplicationDate()))));

        log.info("Price details already calculated for product id: {}, brand id {} and application date: {}",
                priceQueryDto.getProductId(), priceQueryDto.getBrandId(), priceQueryDto.getApplicationDate());

        return priceMapper.pricesToPriceDetailDto(prices);
    }
}
