package com.novice.ecom.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemResponse {

    private Long id;

    private String productName;

    private Integer quantity;

    private BigDecimal price;
    private BigDecimal subTotal;
}
