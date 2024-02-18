package com.example.tarifftracker.application.service;

import com.example.tarifftracker.application.dto.PriceDetailsDto;
import com.example.tarifftracker.application.dto.PriceQueryDto;

public interface PricingService {

    PriceDetailsDto calculatePriceDetails(PriceQueryDto priceQueryDto);
}
