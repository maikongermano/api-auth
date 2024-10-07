package com.loja.auth.api_auth.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "images")
@Data
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(columnDefinition = "TEXT")
    private String url;
    
    @Column(columnDefinition = "TEXT")
    private String base64;
    
    @Lob
    private byte[] blob;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
