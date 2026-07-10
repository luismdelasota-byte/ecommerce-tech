package com.ecommercetech.cart.service;

import com.ecommercetech.cart.dto.CartResponseDTO;
import com.ecommercetech.cart.dto.CartRequestDTO;


import java.util.List;

public interface CartService {
    CartResponseDTO createCart(CartRequestDTO cartRequestDTO);
    List<CartResponseDTO> getAllCarts();
    CartResponseDTO getCartById(Long id);
    CartResponseDTO updateCart(Long id, CartRequestDTO cartRequestDTO);
    void deleteCart(Long id);
}
