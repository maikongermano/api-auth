package com.loja.auth.api_auth.mappers;

import org.springframework.stereotype.Component;

import com.loja.auth.api_auth.model.dto.CompanyDTO;
import com.loja.auth.api_auth.model.entity.Company;

@Component
public class CompanyMapper {

    public CompanyDTO toDTO(Company company) {
        CompanyDTO dto = new CompanyDTO();
        dto.setId(company.getId());
        dto.setName(company.getName());
        dto.setAddress(company.getAddress());
        dto.setClientDescription(company.getClientDescription());
        dto.setTitleHmtl(company.getTitleHmtl());
        dto.setClientDomain(company.getClientDomain());
        dto.setClientLogo(company.getClientLogo());
        dto.setClientName(company.getClientName());
        dto.setUseBackgroundDefaultPage(company.getUseBackgroundDefaultPage());
        dto.setClientBackground(company.getClientBackground());
        return dto;
    }

    public Company toEntity(CompanyDTO companyDTO) {
        Company company = new Company();
        company.setId(companyDTO.getId());
        company.setName(companyDTO.getName());
        company.setAddress(companyDTO.getAddress());
        company.setClientDescription(companyDTO.getClientDescription());
        company.setTitleHmtl(companyDTO.getTitleHmtl());
        company.setClientDomain(companyDTO.getClientDomain());
        company.setClientLogo(companyDTO.getClientLogo());
        company.setClientName(companyDTO.getClientName());
        company.setUseBackgroundDefaultPage(companyDTO.getUseBackgroundDefaultPage());
        company.setClientBackground(companyDTO.getClientBackground());
        return company;
    }
}
