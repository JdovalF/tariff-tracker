package com.example.tarifftracker.infrastructure.adapter.out;

import com.example.tarifftracker.domain.exception.DatabaseNotFoundException;
import com.example.tarifftracker.infrastructure.adapter.repository.BrandRepository;
import com.example.tarifftracker.infrastructure.adapter.repository.PricesRepository;
import com.example.tarifftracker.infrastructure.adapter.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.example.tarifftracker.shared.TestUtils.convertStringToLocalDateTime;
import static com.example.tarifftracker.shared.constants.Constants.*;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PricingDataAdapterTest {

  private Integer brandId, productId;
  private String applicationDate;

  @Mock
  private PricesRepository pricesRepository;
  @Mock
  private ProductRepository productRepository;
  @Mock
  private BrandRepository brandRepository;

  @InjectMocks
  private PricingDataAdapter pricingDataAdapter;

  @BeforeEach
  void setUp() {
    applicationDate = "2020-06-14-00.00.00"; brandId = 1; productId = 1;
  }

  @Test
  void TestFindProductByIdShouldThrowDatabaseNotFoundExceptionWhenProductNotFound() {
    //Given
    var exceptedException = new DatabaseNotFoundException(format(NOT_FOUND_WITH_ID_MESSAGE, PRODUCT_ENTITY_NAME, productId));
    when(productRepository.findById(productId)).thenReturn(Optional.empty());
    //When
    var actualException = assertThrows(DatabaseNotFoundException.class, ()-> pricingDataAdapter.findProductById(productId));
    //Then
    assertEquals(exceptedException.getMessage(), actualException.getMessage());
  }

  @Test
  void TestFindBrandByIdShouldThrowDatabaseNotFoundExceptionWhenBrandNotFound() {
    //Given
    var exceptedException = new DatabaseNotFoundException(format(NOT_FOUND_WITH_ID_MESSAGE, BRAND_ENTITY_NAME, brandId));
    when(brandRepository.findById(productId)).thenReturn(Optional.empty());
    //When
    var actualException = assertThrows(DatabaseNotFoundException.class, ()-> pricingDataAdapter.findBrandById(productId));
    //Then
    assertEquals(exceptedException.getMessage(), actualException.getMessage());
  }

  @Test
  void TestFindPricesByDateAndBrandAndProductShouldThrowDatabaseNotFoundExceptionWhenPriceNotFound() {
    //Given
    LocalDateTime date = convertStringToLocalDateTime(applicationDate);
    var exceptedException = new DatabaseNotFoundException(format(NOT_FOUND_WITH_PARAMETERS_MESSAGE, PRICES_ENTITY_NAME,
        format("Product Id = %s, Brand Id = %s, Application Date %s", productId, brandId, date)));
    when(pricesRepository.findPricesByDateAndBrandAndProduct(brandId, productId, date)).thenReturn(Optional.empty());
    //When
    var actualException = assertThrows(DatabaseNotFoundException.class,
        ()-> pricingDataAdapter.findPricesByDateAndBrandAndProduct(brandId, productId, date));
    //Then
    assertEquals(exceptedException.getMessage(), actualException.getMessage());
  }

}
