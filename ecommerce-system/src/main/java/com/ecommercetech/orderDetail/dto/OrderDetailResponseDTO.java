package com.ecommercetech.orderDetail.dto;

import com.ecommercetech.order.model.Order;
import com.ecommercetech.product.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailResponseDTO {

    private Long id;
    private Long orderId;
    private Long ProductId;
    private String productName;
    private int quantity;
    private double unitPrice;
    private double subtotal;


}
