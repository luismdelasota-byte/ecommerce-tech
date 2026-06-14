package com.ecommercetech.product.mapper;

import com.ecommercetech.product.dto.ProductRequestDTO;
import com.ecommercetech.product.dto.ProductResponseDTO;
import com.ecommercetech.product.model.Product;


public class Mapper {

    // De RequestDTO a Model
    public static Product toModel(ProductRequestDTO dto) {
        if (dto == null) return null;

        return Product.builder()
                .name(dto.getName())
                .category(dto.getCategory())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .description(dto.getDescription())
                .build();
    }

    // De Model a ResponseDTO
    public static ProductResponseDTO toResponseDTO(Product product) {
        if (product == null) return null;

        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .category(product.getCategory())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();
    }
}
