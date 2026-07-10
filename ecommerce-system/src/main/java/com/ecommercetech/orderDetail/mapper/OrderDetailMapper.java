package com.ecommercetech.orderDetail.mapper;


import com.ecommercetech.orderDetail.dto.OrderDetailRequestDTO;
import com.ecommercetech.orderDetail.dto.OrderDetailResponseDTO;
import com.ecommercetech.orderDetail.model.OrderDetail;
import com.ecommercetech.product.model.Product;
import com.ecommercetech.order.model.Order;

public class OrderDetailMapper {

    // Transformar DTO a order
    public static OrderDetail toModel(OrderDetailRequestDTO orderDetailRequestDTO, Order order, Product product){

        if(orderDetailRequestDTO == null) return null;

        return OrderDetail.builder()
                .order(order)
                .product(product)
                .quantity(orderDetailRequestDTO.getQuantity())
                .unitPrice(orderDetailRequestDTO.getUnitPrice())
                .subtotal(orderDetailRequestDTO.getQuantity() * orderDetailRequestDTO.getUnitPrice())
                .build();
    }

    // Transformar el Order a DTO para enviar como respuesta
    public static OrderDetailResponseDTO toResponseDTO(OrderDetail orderDetail){

        if(orderDetail == null) return null;

        return OrderDetailResponseDTO.builder()
                .id(orderDetail.getId())
                .orderId(orderDetail.getOrder().getId())
                .ProductId(orderDetail.getProduct().getId())
                .productName(orderDetail.getProduct().getName())
                .quantity(orderDetail.getQuantity())
                .unitPrice(orderDetail.getUnitPrice())
                .subtotal(orderDetail.getSubtotal())
                .build();
    }
}
