package com.loja.auth.api_auth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.loja.auth.api_auth.model.entity.Auth;
import com.loja.auth.api_auth.model.entity.Company;
import com.loja.auth.api_auth.repository.AuthRepository;
import com.loja.auth.api_auth.repository.CompanyRepository;

@Component
public class DataInitialize implements CommandLineRunner {

    @Autowired
    private AuthRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Criando e salvando a empresa
        Company company = new Company();
        company.setName("Minha Empresa");
        company.setAddress("Rua Exemplo, 123, Cidade Exemplo"); // Definindo um endereço válido

        // Salvando a empresa no banco de dados
        company = companyRepository.save(company);

        // Agora que a empresa foi salva, podemos criar e associar o usuário a essa empresa
        Auth user = new Auth();
        user.setLogin("master");
        user.setPassword(passwordEncoder.encode("master"));
        user.setRole("ADMIN");
        user.setCompany(company); // Associando a empresa ao usuário

        // Salvando o usuário no banco de dados
        userRepository.save(user);

        System.out.println("Usuário criado com empresa associada");
    }
}
