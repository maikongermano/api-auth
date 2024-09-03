package com.loja.auth.api_auth.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.loja.auth.api_auth.model.dto.UserDTO;
import com.loja.auth.api_auth.model.entity.Auth;
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
        dto.setEmpresa(auth.getCompany().getName());
        dto.setCompanyId(auth.getCompany().getId());
        dto.setPassword("");
        return dto;
    }

    public Auth toEntity(UserDTO userDTO) {
        Auth auth = new Auth();
        auth.setId(userDTO.getId());
        auth.setLogin(userDTO.getLogin());
        auth.setRole(userDTO.getRole());

        // Buscar a empresa pelo nome no DTO e associá-la à entidade Auth
        Company company = companyService.findByName(userDTO.getEmpresa());

        if (company == null) {
            throw new RuntimeException("Empresa não encontrada com o nome: " + userDTO.getEmpresa());
        }

        auth.setCompany(company);

        return auth;
    }
}
