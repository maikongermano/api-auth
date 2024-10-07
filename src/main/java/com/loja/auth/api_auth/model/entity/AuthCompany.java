package com.loja.auth.api_auth.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "auth_company")
@Data
public class AuthCompany {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "auth_id")
    private Auth auth;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
}
