package com.loja.auth.api_auth.service;

import com.loja.auth.api_auth.model.entity.Company;
import java.util.Optional;

public interface CompanyService {

    Company save(Company company);
    
    Optional<Company> findById(Long id);
    
    void deleteById(Long id);
    
    Company updateCompany(Long id, Company companyDetails);

    Company findByName(String empresa);
}
