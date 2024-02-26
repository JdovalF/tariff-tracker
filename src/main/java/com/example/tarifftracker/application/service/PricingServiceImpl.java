package com.example.tarifftracker.application.service;


import com.example.tarifftracker.application.dto.PriceDetailsDto;
import com.example.tarifftracker.application.mapper.PriceMapper;
import com.example.tarifftracker.application.port.in.PricingService;
import com.example.tarifftracker.application.port.out.PriceDataPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.example.tarifftracker.shared.util.DateTimeUtil.convertStringToLocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class PricingServiceImpl implements PricingService {

    private final PriceDataPort priceDataPort;
    private final PriceMapper priceMapper;

    @Override
    public PriceDetailsDto calculatePriceDetails(Integer brandId, Integer productId, String applicationDate) {

        log.info("Retrieving product with id: {}", productId);
        var product = priceDataPort.findProductById(productId);

        log.info("Retrieving brand with id: {}", brandId);
        var brand = priceDataPort.findBrandById(brandId);

        log.info("Calculating prices!");
        var localDateTime = convertStringToLocalDateTime(applicationDate);
        var prices = priceDataPort.findPricesByDateAndBrandAndProduct(brand.getBrandId(), product.getProductId(), localDateTime);

        log.info("Price details already calculated for product id: {}, brand id {} and application date: {}",
            productId, brandId, applicationDate);

        return priceMapper.pricesToPriceDetailDto(prices);
    }
}
