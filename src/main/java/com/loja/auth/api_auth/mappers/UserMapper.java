package com.loja.auth.api_auth.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.loja.auth.api_auth.model.dto.UserDTO;
import com.loja.auth.api_auth.model.entity.Auth;
import com.loja.auth.api_auth.model.entity.AuthCompany;
import com.loja.auth.api_auth.model.entity.Company;
import com.loja.auth.api_auth.service.CompanyService;

@Component
public class UserMapper {

    @Autowired
    private CompanyService companyService;

    public UserDTO toDTO(Auth auth) {
        UserDTO dto = new UserDTO();
        dto.setId(auth.getId());
        dto.setLogin(auth.getLogin());
        dto.setRole(auth.getRole());
        List<String> empresas = auth.getAuthCompanies().stream()
                .map(authCompany -> authCompany.getCompany().getName())
                .collect(Collectors.toList());
            dto.setEmpresas(empresas);
        dto.setPassword("");
        return dto;
    }

    public Auth toEntity(UserDTO userDTO) {
        Auth auth = new Auth();
        auth.setId(userDTO.getId());
        auth.setLogin(userDTO.getLogin());
        auth.setRole(userDTO.getRole());

        // Mapeia as empresas do DTO para a entidade Auth
        List<Company> companies = userDTO.getEmpresas().stream()
            .map(companyService::findByName)
            .collect(Collectors.toList());

        // Associa as empresas ao Auth
        List<AuthCompany> authCompanies = userDTO.getEmpresas().stream()
                .map(companyName -> {
                    Company company = companyService.findByName(companyName);
                    AuthCompany authCompany = new AuthCompany();
                    authCompany.setAuth(auth);
                    authCompany.setCompany(company);
                    return authCompany;
                }).collect(Collectors.toList());
        
        auth.setAuthCompanies(authCompanies);

        return auth;
    }
}
