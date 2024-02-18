package com.example.tarifftracker.infrastructure.rest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.tarifftracker.application.dto.PriceQueryDto;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class PricesControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @ParameterizedTest
    @MethodSource("applicationDateVsExpectedPrices")
    public void testRequestAt10AmOn14th(String applicationDate, String expectedStartDate, String expectedEndDate, String expectedPriceListId, String expectedFinalPrice) throws Exception {
        PriceQueryDto priceQueryDto = new PriceQueryDto(applicationDate, "35455", "1");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/prices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(priceQueryDto)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.brandId").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.startDate").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.endDate").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.priceListId").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productId").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.currency").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.brandId").value("1") )
                .andExpect(MockMvcResultMatchers.jsonPath("$.startDate").value(expectedStartDate))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endDate").value(expectedEndDate))
                .andExpect(MockMvcResultMatchers.jsonPath("$.priceListId").value(expectedPriceListId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value("35455"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(expectedFinalPrice))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currency").value("EUR"));
    }

    private static Stream<Arguments> applicationDateVsExpectedPrices() {
        return Stream.of(
                //    Test 1: Request at 10:00 on the 14th for product 35455 for brand 1 (ZARA)
                Arguments.of("2020-06-14-10.00.00", "2020-06-14T00:00:00", "2020-12-31T23:59:59", "1", "35.5"),
                //    Test 2: Request at 16:00 on the 14th for product 35455 for brand 1 (ZARA)
                Arguments.of("2020-06-14-16.00.00", "2020-06-14T15:00:00", "2020-06-14T18:30:00", "2", "25.45"),
                //    Test 3: Request at 21:00 on the 14th for product 35455 for brand 1 (ZARA)
                Arguments.of("2020-06-14-21.00.00", "2020-06-14T00:00:00", "2020-12-31T23:59:59", "1", "35.5"),
                //    Test 4: Request at 10:00 on the 15th for product 35455 for brand 1 (ZARA)
                Arguments.of("2020-06-15-10.00.00", "2020-06-15T00:00:00", "2020-06-15T11:00:00", "3", "30.5"),
                //    Test 5: Request at 21:00 on the 16th for product 35455 for brand 1 (ZARA)
                Arguments.of("2020-06-16-21.00.00", "2020-06-15T16:00:00", "2020-12-31T23:59:59", "4", "38.95")
        );
    }
}