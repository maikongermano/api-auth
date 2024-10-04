package com.loja.auth.api_auth.model.dto;

import lombok.Data;

@Data
public class OrderProductDTO {
    private String productId;
    private Integer quantity;
    private Double price;
    private Double discount;
    private Double totalPrice;
}
