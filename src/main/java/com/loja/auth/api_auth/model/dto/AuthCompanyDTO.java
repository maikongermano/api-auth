package com.loja.auth.api_auth.model.dto;

import lombok.Data;

@Data
public class AuthCompanyDTO {
    private Long id;
    private Long authId;
    private Long companyId;
}