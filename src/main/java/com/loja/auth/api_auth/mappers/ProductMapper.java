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
        dto.setSubDescription(product.getSubDescription());
        dto.setPrice(product.getPrice());
        dto.setCompanyId(product.getCompanyId());
        dto.setAvaliable(product.getAvaliable());
        dto.setIsAvaliable(product.getIsAvaliable());
        dto.setMinQuantity(product.getMinQuantity());
        dto.setIsLastUnits(product.getIsLastUnits());
        dto.setIsDiscount(product.getIsDiscount());
        dto.setDiscount(product.getDiscount());
        dto.setCategory(product.getCategory());
        
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
        product.setSubDescription(productDTO.getSubDescription());
        product.setPrice(productDTO.getPrice());
        product.setCompanyId(productDTO.getCompanyId());
        product.setAvaliable(productDTO.getAvaliable());
        product.setIsAvaliable(productDTO.getIsAvaliable());
        product.setMinQuantity(productDTO.getMinQuantity());
        product.setIsLastUnits(productDTO.getIsLastUnits());
        product.setIsDiscount(productDTO.getIsDiscount());
        product.setDiscount(productDTO.getDiscount());
        product.setCategory(productDTO.getCategory());

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
