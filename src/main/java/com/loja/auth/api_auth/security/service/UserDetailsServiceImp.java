package com.loja.auth.api_auth.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.loja.auth.api_auth.model.entity.Auth;
import com.loja.auth.api_auth.repository.AuthRepository;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private AuthRepository authRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
         Auth auth = authRepository.findByLogin(login);
         return auth;
    }
}

