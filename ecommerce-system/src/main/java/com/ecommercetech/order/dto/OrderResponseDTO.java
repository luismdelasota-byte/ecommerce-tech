package com.ecommercetech.order.dto;


import com.ecommercetech.orderDetail.dto.OrderDetailResponseDTO;
import com.ecommercetech.orderDetail.model.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDTO {

    private Long id;
    private LocalDate date;
    private String state;
    private Long userId; // No es necesario enviar todo el usuario, basta con el id
    private List<OrderDetailResponseDTO> details; // Lista de detalles de la orden
}
