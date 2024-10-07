package com.loja.auth.api_auth.model.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "products")
@Data
public class Product {
    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;
    
    @Column(columnDefinition = "TEXT")
    private String subDescription;

    @Column(nullable = false)
    private Double price;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;
    
    @Column(nullable = false)
    private String avaliable; /* mostrar na loja */
    
    @Column(nullable = false)
    private Boolean isAvaliable = false; /* mostrar icone de avaliação */
    
    @Column(nullable = false)
    private Double minQuantity; /* min quantidade */

    @Column(nullable = false)
    private Boolean isLastUnits = false; /* mostrar icon de ultimas unidades */
    
    @Column(nullable = false)
    private Boolean isDiscount = false; /* mostrar icon de desconto */
    
    @Column(nullable = false)
    private Double discount; /* desconto */
    
    @Column(nullable = false)
    private Boolean category = false; /* Default value */

}
