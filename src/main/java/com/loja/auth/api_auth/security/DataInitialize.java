package com.loja.auth.api_auth.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.loja.auth.api_auth.model.entity.Auth;
import com.loja.auth.api_auth.model.entity.Company;
import com.loja.auth.api_auth.model.enums.Role;
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
        // Verifica se a empresa já existe
        Optional<Company> optionalCompany = companyRepository.findByName("Minha Empresa");

        Company company;
        if (optionalCompany.isEmpty()) {
            // Criando e salvando a empresa
            company = new Company();
            company.setName("Minha Empresa");
            company.setAddress("Rua Exemplo, 123, Cidade Exemplo"); // Definindo um endereço válido
            company.setClientDescription("Levar a moda da rua para fora dela.");
            company.setTitleHmtl("Jhon Roger");
            company.setClientDomain("example.com"); // Substitua pelo domínio real
            company.setClientLogo("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS8GcwXkYZrbCU7lvGmBQyZHDu_KiwX-aKH5Q&s");
            company.setClientName("Jhon Roger");
            company.setUseBackgroundDefaultPage(false);
            company.setClientBackground("https://scontent.fldb10-1.fna.fbcdn.net/v/t39.30808-6/439979087_7430909733661670_8943930251751380713_n.jpg?stp=dst-jpg_s960x960&_nc_cat=101&ccb=1-7&_nc_sid=cc71e4&_nc_ohc=noa41nNvRxkQ7kNvgHtrtnQ&_nc_ht=scontent.fldb10-1.fna&_nc_gid=AAgLcoKb8kmOPhPb6b8fxl1&oh=00_AYAGk5JR3PQkVp6rzeNnAR8m40kdtiNqybFiTN34Wer4rA&oe=66EE8CEA");

            // Salvando a empresa no banco de dados
            company = companyRepository.save(company);
            System.out.println("Empresa criada: " + company.getName());
        } else {
            company = optionalCompany.get();
            System.out.println("Empresa já existe: " + company.getName());
        }

        // Verifica se o usuário já existe
        Auth user = userRepository.findByLogin("master");

        if (user == null) {
            user = new Auth();
            user.setLogin("master");
            user.setPassword(passwordEncoder.encode("master"));
            user.setRole(Role.ADMIN);
            user.setCompany(company); // Associando a empresa ao usuário

            // Salvando o usuário no banco de dados
            userRepository.save(user);
            System.out.println("Usuário criado com empresa associada: " + user.getLogin());
        } else {
            System.out.println("Usuário já existe: " + user.getLogin());
        }
    }
}
