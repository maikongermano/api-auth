package com.loja.auth.api_auth.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loja.auth.api_auth.exception.UserNotFoundException;
import com.loja.auth.api_auth.mappers.UserMapper;
import com.loja.auth.api_auth.model.dto.UserDTO;
import com.loja.auth.api_auth.model.entity.Auth;
import com.loja.auth.api_auth.model.entity.Company;
import com.loja.auth.api_auth.model.enums.Role;
import com.loja.auth.api_auth.service.CompanyService;
import com.loja.auth.api_auth.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private CompanyService companyService;
    
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/{login}")
    public UserDTO getUserByLogin(@PathVariable String login) {
        Auth user = userService.findByLogin(login);
        if (user == null) {
            throw new UserNotFoundException("Usuário não encontrado com o login: " + login);
        }
        return userMapper.toDTO(user);
    }
    
    @GetMapping("/id/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        Optional<Auth> userOptional = userService.findById(id);

        return userOptional.map(userMapper::toDTO)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado com o ID: " + id));
    }

    @PostMapping
    public Auth createUser(@RequestBody UserDTO userDTO) {
    	Auth currentUser = (Auth) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (currentUser.getRole() != Role.ADMIN) {
            throw new RuntimeException("Apenas administradores podem criar novos usuários.");
        }

        Company company = companyService.findById(userDTO.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada com o ID: " + userDTO.getCompanyId()));

        Auth user = userMapper.toEntity(userDTO);
        user.setCompany(company);

        return userService.save(user);
    }

    @PutMapping("/{id}")
    public Auth updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
    	Auth currentUser = (Auth) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    Auth user = userService.findByLogin(userDTO.getLogin());

	    if (user.getRole() == Role.ADMIN && currentUser.getRole() != Role.ADMIN) {
	        throw new RuntimeException("Somente administradores podem alterar a role de outro administrador.");
	    }

	    if (currentUser.getRole() != Role.ADMIN && !currentUser.getId().equals(id)) {
	        throw new RuntimeException("Você não tem permissão para modificar este usuário.");
	    }

	    user.setLogin(userDTO.getLogin());
	    user.setPassword(userDTO.getPassword());

	    if (user.getRole() != Role.ADMIN || (user.getRole() == Role.ADMIN && currentUser.getId().equals(id))) {
	        user.setRole(userDTO.getRole());
	    }

	    Company company = companyService.findById(userDTO.getCompanyId())
	            .orElseThrow(() -> new RuntimeException("Empresa não encontrada com o ID: " + userDTO.getCompanyId()));

	    user.setCompany(company);

	    return userService.save(user);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        Auth user = userService.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado com o ID: " + id));
        
        userService.deleteById(id);
    }
    
    @GetMapping("/{userId}/companies")
    public ResponseEntity<List<Company>> getCompaniesByUserId(@PathVariable Long userId) {
        List<Company> companies = userService.getCompaniesByUserId(userId);
        return ResponseEntity.ok(companies);
    }

}
