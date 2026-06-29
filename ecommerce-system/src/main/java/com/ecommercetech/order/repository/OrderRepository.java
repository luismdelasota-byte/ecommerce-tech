package com.ecommercetech.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommercetech.order.model.Order;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
