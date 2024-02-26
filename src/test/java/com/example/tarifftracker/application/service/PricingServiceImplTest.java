package com.example.tarifftracker.application.service;

import com.example.tarifftracker.application.dto.PriceDetailsDto;
import com.example.tarifftracker.application.mapper.PriceMapper;
import com.example.tarifftracker.application.port.out.PriceDataPort;
import com.example.tarifftracker.domain.exception.DatabaseNotFoundException;
import com.example.tarifftracker.domain.model.Brand;
import com.example.tarifftracker.domain.model.PriceList;
import com.example.tarifftracker.domain.model.Prices;
import com.example.tarifftracker.domain.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static com.example.tarifftracker.shared.TestConstants.*;
import static com.example.tarifftracker.shared.TestUtils.convertStringToLocalDateTime;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PricingServiceImplTest {

  private Integer brandId, productId;
  private String applicationDate;

  @Mock
  private PriceDataPort priceDataPort;
  @Spy
  private final PriceMapper priceMapper = Mappers.getMapper(PriceMapper.class);

  @InjectMocks
  private PricingServiceImpl pricingService;

  @BeforeEach
  void setUp() {
    applicationDate = "2020-06-14-00.00.00"; brandId = 1; productId = 1;
  }

  @Test
  void TestShouldThrowEntityNotFoundExceptionWhenProductNotFound() {
    //Given
    var givenException = new DatabaseNotFoundException(
        format(TEST_NOT_FOUND_WITH_ID_MESSAGE, TEST_PRODUCT_ENTITY_NAME, productId));
    when(priceDataPort.findProductById(productId)).thenThrow(givenException);

    //When
    assertThrows(DatabaseNotFoundException.class,
        ()-> pricingService.calculatePriceDetails(brandId, productId, applicationDate));

    //Then
    verifyNoMoreInteractions(priceDataPort);
  }

  @Test
  void TestShouldThrowEntityNotFoundExceptionWhenBrandNotFound() {
    //Given
    var givenException = new DatabaseNotFoundException(
        format(TEST_NOT_FOUND_WITH_ID_MESSAGE, TEST_BRAND_ENTITY_NAME, brandId));
    when(priceDataPort.findProductById(productId)).thenReturn(new Product());
    when(priceDataPort.findBrandById(brandId)).thenThrow(givenException);

    //When
    assertThrows(DatabaseNotFoundException.class,
        ()-> pricingService.calculatePriceDetails(brandId, productId, applicationDate));

    //Then
    verifyNoMoreInteractions(priceDataPort);
  }

  @Test
  void TestShouldThrowEntityNotFoundExceptionWhenApplicationDateNotFoundInRange() {
    //Given
    var givenProduct = new Product(); givenProduct.setProductId(1);
    var givenbrand = new Brand(); givenbrand.setBrandId(1);
    var givenException = new DatabaseNotFoundException(format(TEST_NOT_FOUND_WITH_PARAMETERS_MESSAGE, TEST_PRICES_ENTITY_NAME,
            format("Product Id = %s, Brand Id = %s, Application Date %s", 1, 1, "2020-06-14-00.00.00")));
    var givenDate = convertStringToLocalDateTime(applicationDate);
    when(priceDataPort.findProductById(productId)).thenReturn(givenProduct);
    when(priceDataPort.findBrandById(brandId)).thenReturn(givenbrand);
    when(priceDataPort.findPricesByDateAndBrandAndProduct(1, 1, givenDate)).thenThrow(givenException);

    //When
    assertThrows(DatabaseNotFoundException.class,
        ()-> pricingService.calculatePriceDetails(brandId, productId, applicationDate));

    //Then
    verifyNoMoreInteractions(priceDataPort);
  }

  @Test
  void TestShouldReturnPriceDetailsDtoWhenHappyPath() {
    //Given
    var givenProduct = new Product(); givenProduct.setProductId(1);
    var givenbrand = new Brand(); givenbrand.setBrandId(1);
    var givenPrices = buildPrices();
    var givenDate = convertStringToLocalDateTime(applicationDate);
    var expectedPriceDetailsDto = buildPriceDetailsDto();
    when(priceDataPort.findProductById(productId)).thenReturn(givenProduct);
    when(priceDataPort.findBrandById(brandId)).thenReturn(givenbrand);
    when(priceDataPort.findPricesByDateAndBrandAndProduct(1, 1, givenDate)).thenReturn(givenPrices);

    //When
    var actualPriceDetailsDto = pricingService.calculatePriceDetails(1, 1, "2020-06-14-00.00.00");

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
    return new Prices(1L, brand, startDate, endDate, pricesList, product, 1, new BigDecimal("30.50"), "EUR");
  }
}