package com.ecommercetech.cart.mapper;

import com.ecommercetech.cart.dto.CartResponseDTO;
import com.ecommercetech.cart.dto.CartRequestDTO;
import com.ecommercetech.cart.model.Cart;
import com.ecommercetech.product.model.Product;
import com.ecommercetech.user.model.User;


public class CartMapper {

    // transformar DTO a model
    public static Cart toModel(CartRequestDTO cartRequestDTO, User user, Product product) {

        if(cartRequestDTO == null) return null;

        return Cart.builder()
                .quantity(cartRequestDTO.getQuantity())
                .unitPrice(cartRequestDTO.getUnitPrice())
                .user(user)
                .product(product)
                .subtotal(cartRequestDTO.getQuantity() * cartRequestDTO.getUnitPrice())
                .build();
    }

    // Transformar model a DTO
    public static CartResponseDTO toResponseDTO(Cart cart) {
        if(cart == null) return null;

        return CartResponseDTO.builder()
                .id(cart.getId())
                .quantity(cart.getQuantity())
                .productName(cart.getProduct().getName())
                .unitPrice(cart.getUnitPrice())
                .userId(cart.getUser().getId())
                .productId(cart.getProduct().getId())
                .subtotal(cart.getSubtotal())
                .build();
    }
}
