package com.loja.auth.api_auth.service.impl;

import com.loja.auth.api_auth.model.entity.Company;
import com.loja.auth.api_auth.repository.CompanyRepository;
import com.loja.auth.api_auth.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public Company save(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public Optional<Company> findById(Long id) {
        return companyRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        companyRepository.deleteById(id);
    }

    @Override
    public Company updateCompany(Long id, Company companyDetails) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada com o ID: " + id));

        company.setName(companyDetails.getName());
        company.setAddress(companyDetails.getAddress());
        
        return companyRepository.save(company);
    }

    @Override
    public Company findByName(String name) {
        return companyRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada com o nome: " + name));
    }


}
