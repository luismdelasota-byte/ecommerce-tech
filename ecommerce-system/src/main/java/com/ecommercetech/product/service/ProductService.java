package com.ecommercetech.product.service;

import com.ecommercetech.product.dto.ProductResponseDTO;
import com.ecommercetech.product.dto.ProductRequestDTO;

import java.util.List;

public interface ProductService {

    List<ProductResponseDTO> getAllProducts(); //Mostrar todos los productos
    ProductResponseDTO getProductById(Long id);// Mostrar producto por id
    ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO); //Crear producto, con los datos que ingresa en "ProductRequestDTO productRequestDTO"
    ProductResponseDTO updateProduct(Long id, ProductRequestDTO productRequestDTO); //Actualizar producto, con los nuevos datos que ingresa en "ProductRequestDTO productRequestDTO"
    void deleteProduct(Long id); //Eliminar producto
}


/* Contrato claro: la interfaz define que operaciones debe tener el servicio(crear, actualizar, eliminar, etc)
 * Solo define que hacer mas no como hacerlo
 * Separacion de responsabilidad
 * Flexibilidad y mantenibilidad : Si queremos cambiar la logica, solo cambiamos la implementacion
 * Buenas practicas de Spring : Spring recomienda programar contra interfaces, no contra clases concretas*/