package com.ecommercetech.orderDetail.service;

import com.ecommercetech.orderDetail.dto.OrderDetailRequestDTO;
import com.ecommercetech.orderDetail.dto.OrderDetailResponseDTO;

import java.util.List;

public interface OrderDetailService {

    OrderDetailResponseDTO createOrderDetail(OrderDetailRequestDTO orderDetailRequestDTO);
    List<OrderDetailResponseDTO> getAllOrderDetails();
    OrderDetailResponseDTO getOrderDetailById(Long id);
    OrderDetailResponseDTO updateOrderDetail(Long id, OrderDetailRequestDTO orderDetailRequestDTO);
    void deleteOrderDetail(Long id);
}
