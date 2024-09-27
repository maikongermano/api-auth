package com.loja.auth.api_auth.model.dto;

import lombok.Data;

@Data
public class ImageDTO {
	
	private Long id;
    private String url;
    private String base64;
    private byte[] blob;

}
