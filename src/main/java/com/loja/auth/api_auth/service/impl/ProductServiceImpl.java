package com.loja.auth.api_auth.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.loja.auth.api_auth.model.entity.Image;
import com.loja.auth.api_auth.model.entity.Product;
import com.loja.auth.api_auth.repository.ProductRepository;
import com.loja.auth.api_auth.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product createProduct(Product product) {
    	if (product.getImages() != null) {
            for (Image image : product.getImages()) {
                image.setProduct(product);
            }
        }
        return productRepository.save(product);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com o id: " + id));
    }

    @Override
    public Page<Product> listProducts(int page, int size) {
        return productRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        Product existingProduct = getProductById(id);
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setImages(product.getImages());
        existingProduct.setAvaliable(product.getAvaliable());
        existingProduct.setIsAvaliable(product.getIsAvaliable());
        existingProduct.setMinQuantity(product.getMinQuantity());
        existingProduct.setIsLastUnits(product.getIsLastUnits());
        existingProduct.setIsDiscount(product.getIsDiscount());
        existingProduct.setDiscount(product.getDiscount());
        existingProduct.setCategory(product.getCategory()); 
        return productRepository.save(existingProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
    
    @Override
    public List<Product> findProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

}
