package com.loja.auth.api_auth.repository;

import org.springframework.data.repository.CrudRepository;

import com.loja.auth.api_auth.model.entity.AuthCompany;

public interface AuthCompanyRepository extends CrudRepository<AuthCompany, Long> {

}
