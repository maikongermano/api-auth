package com.loja.auth.api_auth.repository;

import org.springframework.data.repository.CrudRepository;

import com.loja.auth.api_auth.model.entity.Auth;

public interface AuthRepository extends CrudRepository<Auth, Long>{
	
	Auth findByLogin(String login);

}
