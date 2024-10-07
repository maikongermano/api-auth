package com.loja.auth.api_auth.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.loja.auth.api_auth.mappers.ProductMapper;
import com.loja.auth.api_auth.model.dto.ProductDTO;
import com.loja.auth.api_auth.model.entity.Product;
import com.loja.auth.api_auth.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMapper productMapper;

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        Product createdProduct = productService.createProduct(product);
        ProductDTO createdProductDTO = productMapper.toDTO(createdProduct);
        return new ResponseEntity<>(createdProductDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        ProductDTO productDTO = productMapper.toDTO(product);
        return ResponseEntity.ok(productDTO);
    }

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> listProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Product> productPage = productService.listProducts(page, size);
        Page<ProductDTO> productDTOPage = productPage.map(productMapper::toDTO);
        return ResponseEntity.ok(productDTOPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        Product updatedProduct = productService.updateProduct(id, product);
        ProductDTO updatedProductDTO = productMapper.toDTO(updatedProduct);
        return ResponseEntity.ok(updatedProductDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> searchProductsByName(@RequestParam String name) {
        List<Product> products = productService.findProductsByName(name);
        List<ProductDTO> productDTOs = products.stream()
            .map(productMapper::toDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(productDTOs);
    }

}
