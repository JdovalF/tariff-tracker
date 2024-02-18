package com.example.tarifftracker.application.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PriceQueryDto {

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}-\\d{2}\\.\\d{2}\\.\\d{2}$",
            message = "Application Date must be in the format YYYY-MM-DD-HH.MM.SS")
    private String applicationDate;

    @Pattern(regexp = "^[0-9]+$", message = "Product ID must be a number")
    private String productId;

    @Pattern(regexp = "^[0-9]+$", message = "Brand ID must be a number")
    private String brandId;
}
