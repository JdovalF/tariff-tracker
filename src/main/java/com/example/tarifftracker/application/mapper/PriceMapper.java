package com.example.tarifftracker.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.example.tarifftracker.application.dto.PriceDetailsDto;
import com.example.tarifftracker.domain.entity.Prices;

@Mapper(componentModel = "spring")
public interface PriceMapper {

    @Mapping(target = "brandId", source = "prices", qualifiedByName = "brandId")
    @Mapping(target = "priceListId", source = "prices", qualifiedByName = "priceListId")
    @Mapping(target = "productId", source = "prices", qualifiedByName = "productId")
    @Mapping(target = "currency", source = "prices", qualifiedByName = "curr")
    PriceDetailsDto pricesToPriceDetailDto(Prices prices);

    @Named("brandId")
    default Integer getBrandIdFromPrices(Prices prices) {
        return prices.getBrand() != null ? prices.getBrand().getBrandId() : null;
    }

    @Named("priceListId")
    default Integer getPricesListIdFromPrices(Prices prices) {
        return prices.getPriceList() != null ? prices.getPriceList().getPriceListId() : null;
    }

    @Named("productId")
    default Integer getProductIdFromPrices(Prices prices) {
        return prices.getProduct() != null ? prices.getProduct().getProductId() : null;
    }

    @Named("curr")
    default String getCurrFromPrices(Prices prices) {
        return prices.getCurr() != null ? prices.getCurr() : null;
    }
}
