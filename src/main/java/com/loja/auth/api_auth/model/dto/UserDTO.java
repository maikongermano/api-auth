package com.loja.auth.api_auth.model.dto;

import com.loja.auth.api_auth.model.enums.Role;

import lombok.Data;

@Data
public class UserDTO {
	
	private Long id;
    private String login;
    private Role role;
    private String empresa;
	private Long companyId;
	private String password;

}
