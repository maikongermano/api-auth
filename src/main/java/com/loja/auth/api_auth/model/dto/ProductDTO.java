package com.loja.auth.api_auth.model.dto;

import java.util.List;

import lombok.Data;

@Data
public class ProductDTO {
	
	private Long id;
    private String name;
    private String description;
    private Double price;
    private Long companyId;
    private List<ImageDTO> images;

}
