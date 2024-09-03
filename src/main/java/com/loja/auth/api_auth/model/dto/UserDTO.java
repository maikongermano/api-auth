package com.loja.auth.api_auth.model.dto;

import lombok.Data;

@Data
public class UserDTO {
	
	private Long id;
    private String login;
    private String role;
    private String empresa;
	private Long CompanyId;
	private String password;

}
