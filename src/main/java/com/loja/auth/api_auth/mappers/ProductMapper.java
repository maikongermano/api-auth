package com.loja.auth.api_auth.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.loja.auth.api_auth.model.dto.ImageDTO;
import com.loja.auth.api_auth.model.dto.ProductDTO;
import com.loja.auth.api_auth.model.entity.Image;
import com.loja.auth.api_auth.model.entity.Product;

@Component
public class ProductMapper {

    public ProductDTO toDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setCompanyId(product.getCompanyId());
        
        List<ImageDTO> imageDTOs = product.getImages().stream()
            .map(this::toImageDTO)
            .collect(Collectors.toList());
        
        dto.setImages(imageDTOs);
        return dto;
    }

    public Product toEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setCompanyId(productDTO.getCompanyId());

        List<Image> images = productDTO.getImages().stream()
            .map(this::toImageEntity)
            .collect(Collectors.toList());
        
        product.setImages(images);
        return product;
    }

    private ImageDTO toImageDTO(Image image) {
        ImageDTO dto = new ImageDTO();
        dto.setId(image.getId());
        dto.setUrl(image.getUrl());
        dto.setBase64(image.getBase64());
        dto.setBlob(image.getBlob());
        return dto;
    }

    private Image toImageEntity(ImageDTO imageDTO) {
        Image image = new Image();
        image.setId(imageDTO.getId());
        image.setUrl(imageDTO.getUrl());
        image.setBase64(imageDTO.getBase64());
        image.setBlob(imageDTO.getBlob());
        return image;
    }
}
