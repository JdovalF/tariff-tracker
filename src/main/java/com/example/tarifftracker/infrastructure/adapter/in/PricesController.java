package com.example.tarifftracker.infrastructure.adapter.in;

import com.example.tarifftracker.application.dto.PriceDetailsDto;
import com.example.tarifftracker.application.port.in.PricingService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.example.tarifftracker.shared.constants.Constants.DATE_REGEX;
import static com.example.tarifftracker.shared.constants.Constants.DATE_REGEX_ERROR_MESSAGE;

@RestController
@RequestMapping("/api/v1/prices")
@RequiredArgsConstructor
@Slf4j
public class PricesController {

    private final PricingService pricingService;

    @GetMapping
    public ResponseEntity<PriceDetailsDto> calculatePriceDetails(
        @RequestParam @Valid @NotNull Integer productId,
        @RequestParam @Valid @NotNull Integer brandId,
        @RequestParam @Valid @Pattern(regexp = DATE_REGEX, message = DATE_REGEX_ERROR_MESSAGE) String applicationDate) {

        log.info("Requesting price for product id: {}, brand id {} and application date: {}",
            productId, brandId, applicationDate);
        return ResponseEntity.ok(pricingService.calculatePriceDetails(brandId, productId, applicationDate));
    }

}
