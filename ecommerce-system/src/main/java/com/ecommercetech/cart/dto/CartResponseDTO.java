package com.ecommercetech.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartResponseDTO {

    private Long id;
    private int quantity;
    private String productName;
    private double subtotal;
    private Long productId;
    private Long userId;
    private double unitPrice;
}
