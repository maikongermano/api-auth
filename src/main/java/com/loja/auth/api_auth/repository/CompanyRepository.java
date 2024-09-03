package com.loja.auth.api_auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loja.auth.api_auth.model.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Long>{

	Optional<Company> findByName(String name);

}
