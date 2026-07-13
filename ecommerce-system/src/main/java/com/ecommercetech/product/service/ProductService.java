package com.ecommercetech.product.service;

import com.ecommercetech.product.dto.ProductRequestDTO;
import com.ecommercetech.product.dto.ProductResponseDTO;

import java.util.List;

public interface ProductService {

    ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO);
    List<ProductResponseDTO> getAllProducts();
    ProductResponseDTO getProductById(Long id);
    ProductResponseDTO updateProduct(ProductRequestDTO productRequestDTO, Long id);
    void deleteProduct(Long id);
}
