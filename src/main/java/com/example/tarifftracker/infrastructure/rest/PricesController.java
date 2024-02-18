package com.example.tarifftracker.infrastructure.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tarifftracker.application.dto.PriceDetailsDto;
import com.example.tarifftracker.application.dto.PriceQueryDto;
import com.example.tarifftracker.application.service.PricingService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/prices")
@RequiredArgsConstructor
@Slf4j
public class PricesController {

    private final PricingService pricingService;

    @PostMapping
    public ResponseEntity<PriceDetailsDto> calculatePriceDetails(@RequestBody @Valid PriceQueryDto priceQueryDto) {

        log.info("Requesting price for product id: {}, brand id {} and application date: {}",
            priceQueryDto.getProductId(), priceQueryDto.getBrandId(), priceQueryDto.getApplicationDate());

        return ResponseEntity.ok(pricingService.calculatePriceDetails(priceQueryDto));
    }
}
