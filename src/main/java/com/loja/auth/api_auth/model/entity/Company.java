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
    
    @Column
    private String clientDescription;
    
    @Column
    private String titleHmtl;
    
    @Column
    private String clientDomain;
    
    @Column
    private String clientLogo;
    
    @Column
    private String clientName;
    
    @Column
    private Boolean useBackgroundDefaultPage;
    
    @Column
    private String clientBackground;
}
