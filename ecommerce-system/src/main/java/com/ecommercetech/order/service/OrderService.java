package com.ecommercetech.order.service;

import com.ecommercetech.order.dto.OrderRequestDTO;
import com.ecommercetech.order.dto.OrderResponseDTO;
import java.util.List;

public interface OrderService {

    OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO); //Crear orden
    List<OrderResponseDTO> getAllOrders();
    OrderResponseDTO getOrderById(Long id);
    OrderResponseDTO updateOrder(OrderRequestDTO orderRequestDTO, Long id);
    void deleteOrder(Long id);
}
