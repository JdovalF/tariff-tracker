package com.example.tarifftracker.domain.service;

import static com.example.tarifftracker.shared.TestConstants.TEST_BRAND_ENTITY_NAME;
import static com.example.tarifftracker.shared.TestConstants.TEST_NOT_FOUND_WITH_ID_MESSAGE;
import static com.example.tarifftracker.shared.TestConstants.TEST_NOT_FOUND_WITH_PARAMETERS_MESSAGE;
import static com.example.tarifftracker.shared.TestConstants.TEST_PRICES_ENTITY_NAME;
import static com.example.tarifftracker.shared.TestConstants.TEST_PRODUCT_ENTITY_NAME;
import static com.example.tarifftracker.shared.TestUtils.convertStringToLocalDateTime;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.tarifftracker.application.dto.PriceDetailsDto;
import com.example.tarifftracker.application.dto.PriceQueryDto;
import com.example.tarifftracker.application.mapper.PriceMapper;
import com.example.tarifftracker.domain.entity.Brand;
import com.example.tarifftracker.domain.entity.PriceList;
import com.example.tarifftracker.domain.entity.Prices;
import com.example.tarifftracker.domain.entity.Product;
import com.example.tarifftracker.domain.exception.DatabaseNotFoundException;
import com.example.tarifftracker.domain.repository.BrandRepository;
import com.example.tarifftracker.domain.repository.PricesRepository;
import com.example.tarifftracker.domain.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class PricingServiceImplTest {

    private PriceQueryDto givenPriceQuerydto;
    @Mock
    private PricesRepository pricesRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private BrandRepository brandRepository;

    @Spy
    private final PriceMapper priceMapper = Mappers.getMapper(PriceMapper.class);

    @InjectMocks
    private PricingServiceImpl pricingService;

    @BeforeEach
    void setUp() {
        givenPriceQuerydto = new PriceQueryDto("2020-06-14-00.00.00", "1", "1");
    }


    @Test
    void TestShouldThrowEntityNotFoundExceptionWhenProductNotFound() {
        //Given
        var expectedException = new DatabaseNotFoundException(
                format(TEST_NOT_FOUND_WITH_ID_MESSAGE, TEST_PRODUCT_ENTITY_NAME, givenPriceQuerydto.getProductId()));
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        //When
        var actualException = assertThrows(DatabaseNotFoundException.class,
                () -> pricingService.calculatePriceDetails(givenPriceQuerydto));

        //Then
        assertEquals(expectedException.getMessage(), actualException.getMessage());
    }

    @Test
    void TestShouldThrowEntityNotFoundExceptionWhenBrandNotFound() {
        //Given
        var expectedException = new DatabaseNotFoundException(
                format(TEST_NOT_FOUND_WITH_ID_MESSAGE, TEST_BRAND_ENTITY_NAME, givenPriceQuerydto.getBrandId()));
        when(productRepository.findById(1)).thenReturn(Optional.of(new Product()));
        when(brandRepository.findById(1)).thenReturn(Optional.empty());

        //When
        var actualException = assertThrows(DatabaseNotFoundException.class,
                () -> pricingService.calculatePriceDetails(givenPriceQuerydto));

        //Then
        assertEquals(expectedException.getMessage(), actualException.getMessage());
    }

    @Test
    void TestShouldThrowEntityNotFoundExceptionWhenApplicationDateNotFoundInRange() {
        //Given
        var givenProduct = new Product(); givenProduct.setProductId(1);
        var givenbrand = new Brand(); givenbrand.setBrandId(1);
        var expectedException = new DatabaseNotFoundException(
                format(TEST_NOT_FOUND_WITH_PARAMETERS_MESSAGE, TEST_PRICES_ENTITY_NAME, format("Product Id = %s, Brand Id = %s, Application Date %s", 1, 1, "2020-06-14-00.00.00") ));
        var givenDate = convertStringToLocalDateTime(givenPriceQuerydto.getApplicationDate());
        when(productRepository.findById(1)).thenReturn(Optional.of(givenProduct));
        when(brandRepository.findById(1)).thenReturn(Optional.of(givenbrand));
        when(pricesRepository.findPricesByDateAndBrandAndProduct(1, 1, givenDate)).thenReturn(Optional.empty());

        //When
        var actualException = assertThrows(DatabaseNotFoundException.class, () -> pricingService.calculatePriceDetails(givenPriceQuerydto));

        //Then
        assertEquals(expectedException.getMessage(), actualException.getMessage());
    }

    @Test
    void TestShouldReturnPriceDetailsDtoWhenHappyPath() {
        //Given
        var givenProduct = new Product(); givenProduct.setProductId(1);
        var givenbrand = new Brand(); givenbrand.setBrandId(1);
        var givenPrices = buildPrices();
        var givenDate = convertStringToLocalDateTime(givenPriceQuerydto.getApplicationDate());
        var expectedPriceDetailsDto = buildPriceDetailsDto();
        when(productRepository.findById(1)).thenReturn(Optional.of(givenProduct));
        when(brandRepository.findById(1)).thenReturn(Optional.of(givenbrand));
        when(pricesRepository.findPricesByDateAndBrandAndProduct(1, 1, givenDate)).thenReturn(Optional.of(givenPrices));

        //When
        var actualPriceDetailsDto = pricingService.calculatePriceDetails(givenPriceQuerydto);

        //Then
        assertEquals(expectedPriceDetailsDto, actualPriceDetailsDto);
    }

    private static PriceDetailsDto buildPriceDetailsDto() {
        return PriceDetailsDto.builder()
                .productId(1)
                .brandId(1)
                .priceListId(1)
                .startDate(convertStringToLocalDateTime("2020-06-14-00.00.00"))
                .endDate(convertStringToLocalDateTime("2020-12-31-23.59.59"))
                .price(new BigDecimal("30.50"))
                .currency("EUR")
                .build();
    }

    private Prices buildPrices() {
        var product = new Product(); product.setProductId(1);
        var brand = new Brand(); brand.setBrandId(1);
        var startDate = convertStringToLocalDateTime("2020-06-14-00.00.00");
        var endDate = convertStringToLocalDateTime("2020-12-31-23.59.59");
        var pricesList = new PriceList(); pricesList.setPriceListId(1);
        var prices = new Prices(1L, brand, startDate, endDate, pricesList, product, 1, new BigDecimal("30.50"), "EUR");
        return prices;
    }
}