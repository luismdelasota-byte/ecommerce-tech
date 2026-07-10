package com.ecommercetech.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommercetech.cart.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
