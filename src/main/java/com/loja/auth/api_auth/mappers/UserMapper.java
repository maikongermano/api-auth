package com.loja.auth.api_auth.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.loja.auth.api_auth.model.dto.AuthCompanyDTO;
import com.loja.auth.api_auth.model.dto.UserDTO;
import com.loja.auth.api_auth.model.entity.Auth;
import com.loja.auth.api_auth.model.entity.AuthCompany;

@Component
public class UserMapper {

    
    @Autowired
    private AuthCompanyMapper companyMapper;

    public UserDTO toDTO(Auth auth) {
        UserDTO dto = new UserDTO();
        dto.setId(auth.getId());
        dto.setLogin(auth.getLogin());
        dto.setRole(auth.getRole());
        List<AuthCompanyDTO> companiesDTO = auth.getAuthCompanies().stream()
                .map(companyMapper::toDTO)
                .collect(Collectors.toList());
        dto.setAuthCompany(companiesDTO);
        dto.setPassword("");
        return dto;
    }

    public Auth toEntity(UserDTO userDTO) {
        Auth auth = new Auth();
        auth.setId(userDTO.getId());
        auth.setLogin(userDTO.getLogin());
        auth.setRole(userDTO.getRole());
        List<AuthCompany> authCompanies = userDTO.getAuthCompany().stream()
                .map(companyMapper::toEntity)
                .collect(Collectors.toList());

        auth.setAuthCompanies(authCompanies);

        return auth;
    }
}
