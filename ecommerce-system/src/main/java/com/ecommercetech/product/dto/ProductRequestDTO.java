package com.ecommercetech.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequestDTO {

    private Long id;
    private String name;
    private String description;
    private String category;
    private double price;
    private int stock;

}
