package com.ecommercetech.orderDetail.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommercetech.orderDetail.model.OrderDetail;

public interface OrderDetailRespository extends JpaRepository<OrderDetail, Long> {
}
