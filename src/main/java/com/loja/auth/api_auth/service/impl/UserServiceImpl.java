package com.loja.auth.api_auth.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loja.auth.api_auth.model.entity.Auth;
import com.loja.auth.api_auth.model.entity.AuthCompany;
import com.loja.auth.api_auth.repository.AuthRepository;
import com.loja.auth.api_auth.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AuthRepository authRepository;

    @Override
    public Optional<Auth> findById(Long id) {
        return authRepository.findById(id);
    }

    @Override
    public Auth findByLogin(String login) {
        return authRepository.findByLogin(login);
    }

    @Override
    public List<Auth> findAll() {
        return (List<Auth>) authRepository.findAll();
    }

    @Override
    public Auth save(Auth auth) {
        return authRepository.save(auth);
    }

    @Override
    public void deleteById(Long id) {
        authRepository.deleteById(id);
    }
    
    @Override
    public List<AuthCompany> getCompaniesByUserId(Long userId) {
        Auth user = authRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + userId));
        return user.getAuthCompanies();
    }
}
