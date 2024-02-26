package com.example.tarifftracker.application.port.in;

import com.example.tarifftracker.application.dto.PriceDetailsDto;

public interface PricingService {

    PriceDetailsDto calculatePriceDetails(Integer brandId, Integer productId, String applicationDate);
}
