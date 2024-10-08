package com.loja.auth.api_auth.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.loja.auth.api_auth.model.dto.AuthCompanyDTO;
import com.loja.auth.api_auth.model.entity.AuthCompany;
import com.loja.auth.api_auth.service.UserService;
import com.loja.auth.api_auth.service.CompanyService;

@Component
public class AuthCompanyMapper {

    @Autowired
    private UserService authService;

    @Autowired
    private CompanyService companyService;

    public AuthCompanyDTO toDTO(AuthCompany authCompany) {
        AuthCompanyDTO dto = new AuthCompanyDTO();
        dto.setId(authCompany.getId());
        dto.setAuthId(authCompany.getAuth().getId());
        dto.setCompanyId(authCompany.getCompany().getId());
        return dto;
    }

    public AuthCompany toEntity(AuthCompanyDTO authCompanyDTO) {
        AuthCompany authCompany = new AuthCompany();
        authCompany.setId(authCompanyDTO.getId());

        // Busca as entidades Auth e Company pelo ID
        authCompany.setAuth(authService.findById(authCompanyDTO.getAuthId())
                .orElseThrow(() -> new RuntimeException("Auth não encontrado com ID: " + authCompanyDTO.getAuthId())));
        
        authCompany.setCompany(companyService.findById(authCompanyDTO.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company não encontrada com ID: " + authCompanyDTO.getCompanyId())));

        return authCompany;
    }
}
