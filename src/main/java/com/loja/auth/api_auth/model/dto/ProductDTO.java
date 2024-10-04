package com.loja.auth.api_auth.model.dto;

import java.util.List;

import lombok.Data;

@Data
public class ProductDTO {
	
	private Long id;
    private String name;
    private String description;
    private String subDescription;
    private Double price;
    private Long companyId;
    private List<ImageDTO> images;
    private String avaliable;
    private Boolean isAvaliable;
    private Double minQuantity;
    private Boolean isLastUnits;
    private Boolean isDiscount;
    private Double discount;
    private Boolean category;

}
