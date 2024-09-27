package com.loja.auth.api_auth.service;

import org.springframework.data.domain.Page;

import com.loja.auth.api_auth.model.entity.Product;

public interface ProductService {
    Product createProduct(Product product);
    Product getProductById(Long id);
    Page<Product> listProducts(int page, int size);
    Product updateProduct(Long id, Product product);
    void deleteProduct(Long id);
}
