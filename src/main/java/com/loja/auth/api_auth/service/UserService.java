package com.loja.auth.api_auth.service;

import java.util.List;
import java.util.Optional;

import com.loja.auth.api_auth.model.entity.Auth;
import com.loja.auth.api_auth.model.entity.Company;

public interface UserService {

    Optional<Auth> findById(Long id);
    Auth findByLogin(String login);
    List<Auth> findAll();
    Auth save(Auth auth);
    void deleteById(Long id);
    List<Company> getCompaniesByUserId(Long userId);
}
