package com.example.tarifftracker.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Prices {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "BRAND_ID")
    @NotNull
    private Brand brand;

    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "PRICE_LIST_ID")
    @NotNull
    private PriceList priceList;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    @NotNull
    private Product product;

    @NotNull
    private Integer priority;

    @Column(name = "PRICE", precision = 10, scale = 2)
    @NotNull
    private BigDecimal price;

    @NotNull
    @Size(min = 3, max = 3)
    private String curr;
}
