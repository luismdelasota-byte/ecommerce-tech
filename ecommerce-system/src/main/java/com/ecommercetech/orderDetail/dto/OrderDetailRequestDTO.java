package com.ecommercetech.orderDetail.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.ecommercetech.product.model.Product;
import com.ecommercetech.order.model.Order;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailRequestDTO {

    private Long id;
    private Long orderId;
    private Long productId;
    private int unitPrice;

}
