package com.ecommercetech.order.mapper;

import com.ecommercetech.order.dto.OrderRequestDTO;
import com.ecommercetech.order.dto.OrderResponseDTO;
import com.ecommercetech.orderDetail.mapper.OrderDetailMapper;
import com.ecommercetech.user.model.User;
import com.ecommercetech.order.model.Order;
import java.util.stream.Collectors;

public class OrderMapper {

    // Transformar DTO -> Order
    public static Order toModel(OrderRequestDTO orderRequestDTO, User user){
        if(orderRequestDTO == null) return null;

        return Order.builder()
                .date(orderRequestDTO.getDate())
                .state(orderRequestDTO.getState())
                .user(user)
                .build();
    }

    // Mapper de salida: Entidad -> RespondeDTO
    public static OrderResponseDTO toResponseDTO(Order order){
        if(order == null) return null;

        return OrderResponseDTO.builder()
                .id(order.getId())
                .date(order.getDate())
                .state(order.getState())
                .userId(order.getUser().getId())
                .details(order.getDetails().stream()
                        .map(OrderDetailMapper::toResponseDTO)
                        .collect(Collectors.toList()))
                .build();
    }
}
