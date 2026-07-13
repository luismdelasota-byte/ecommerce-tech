package com.ecommercetech.pay.mapper;

import com.ecommercetech.pay.dto.*;
import com.ecommercetech.pay.model.Pay;
import com.ecommercetech.order.model.Order;

public class PayMapper {

    public static Pay toModel(PayRequestDTO dto, Order order) {
        if (dto == null) return null;
        return Pay.builder()
                .id(dto.getId())
                .order(order)
                .paymentMethod(dto.getPaymentMethod())
                .state(dto.getState() != null ? dto.getState() : "pending")
                .build();
    }

    public static PayResponseDTO toResponseDTO(Pay entity) {
        if (entity == null) return null;
        return PayResponseDTO.builder()
                .id(entity.getId())
                .orderId(entity.getOrder().getId())
                .paymentMethod(entity.getPaymentMethod())
                .state(entity.getState())
                .build();
    }
}
