package com.loja.auth.api_auth.model.dto;

import lombok.Data;

@Data
public class OrderProductDTO {
	private CompanyDTO company;
    private OrderDTO order;
    private ProductDTO product;
    private Integer quantity;
    private Double price;
    private Double discount;
    private Double totalPrice;
}
