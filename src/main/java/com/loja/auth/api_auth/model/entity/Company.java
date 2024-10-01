package com.loja.auth.api_auth.model.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "company")
@Data
public class Company implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String address;

    @Column(columnDefinition = "TEXT")
    private String clientDescription;

    @Column(columnDefinition = "TEXT")
    private String titleHmtl;

    @Column(columnDefinition = "TEXT")
    private String clientDomain;

    @Column(columnDefinition = "TEXT")
    private String clientLogo;

    @Column(columnDefinition = "TEXT")
    private String clientName;

    @Column
    private Boolean useBackgroundDefaultPage;

    @Column(columnDefinition = "TEXT")
    private String clientBackground;
}
