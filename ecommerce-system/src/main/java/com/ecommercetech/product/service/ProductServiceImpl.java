package com.ecommercetech.product.service;

import com.ecommercetech.product.dto.ProductResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ecommercetech.product.repository.ProductRepository;
import com.ecommercetech.product.dto.ProductRequestDTO;

import com.ecommercetech.product.model.Product;
import com.ecommercetech.product.mapper.ProductMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        Product product = ProductMapper.toModel(productRequestDTO);
        return ProductMapper.toResponseDTO(productRepository.save(product));
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductMapper::toResponseDTO)
                .toList();
    }

    @Override
    public ProductResponseDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id " + id));
        return ProductMapper.toResponseDTO(product);
    }

    @Override
    public ProductResponseDTO updateProduct(ProductRequestDTO productRequestDTO, Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id " + id));
        product.setCategory(productRequestDTO.getCategory());
        product.setDescription(productRequestDTO.getDescription());
        product.setName(productRequestDTO.getName());
        product.setPrice(productRequestDTO.getPrice());
        product.setStock(productRequestDTO.getStock());

        return ProductMapper.toResponseDTO(productRepository.save(product));
    }

    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado con id " + id);
        }
        productRepository.deleteById(id);
    }

}
