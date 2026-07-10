package com.ecommercetech.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartRequestDTO {

    private Long id;
    private int quantity;
    private Long productId;
    private Long userId;
    private double unitPrice;
}
