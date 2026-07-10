package com.ecommercetech.cart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.ecommercetech.cart.dto.CartRequestDTO;
import com.ecommercetech.cart.dto.CartResponseDTO;

import java.util.List;
import com.ecommercetech.cart.service.CartService;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CartResponseDTO> createCart(@RequestBody CartRequestDTO cartRequestDTO) {
        return ResponseEntity.ok(cartService.createCart(cartRequestDTO));
    }

    @GetMapping
    public ResponseEntity<List<CartResponseDTO>> getAllCarts() {
        return ResponseEntity.ok(cartService.getAllCarts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartResponseDTO> getCartById(@PathVariable Long id) {
        return ResponseEntity.ok(cartService.getCartById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartResponseDTO> updateCart(@PathVariable Long id, @RequestBody CartRequestDTO cartRequestDTO) {
        return ResponseEntity.ok(cartService.updateCart(id, cartRequestDTO));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCart(@PathVariable Long id) {
        cartService.deleteCart(id);
        return ResponseEntity.noContent().build();
    }
}
