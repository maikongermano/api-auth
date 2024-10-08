package com.loja.auth.api_auth.model.dto;

import java.util.List;

import com.loja.auth.api_auth.model.enums.Role;

import lombok.Data;

@Data
public class UserDTO {
	
	private Long id;
    private String login;
    private Role role;
    private List<AuthCompanyDTO> authCompany;
	private Long companyId;
	private String password;

}
