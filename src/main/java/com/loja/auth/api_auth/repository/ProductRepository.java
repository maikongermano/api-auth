package com.loja.auth.api_auth.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.loja.auth.api_auth.model.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByCompanyId(Long companyId, Pageable pageable);
}
