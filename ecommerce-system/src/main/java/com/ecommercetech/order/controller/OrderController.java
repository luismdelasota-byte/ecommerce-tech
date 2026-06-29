package com.ecommercetech.order.controller;

import com.ecommercetech.order.dto.OrderRequestDTO;
import com.ecommercetech.order.dto.OrderResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ecommercetech.order.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    //Post -> Crear order
    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderRequestDTO orderRequestDTO){
        return ResponseEntity.ok(orderService.createOrder(orderRequestDTO));
    }

    // Mostrar order
    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders(){
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    // Mostrar order por id
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrdersById(@PathVariable Long id){
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    // Modificar order
    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> updateOrder(@RequestBody OrderRequestDTO orderRequestDTO, @PathVariable Long id){
        return ResponseEntity.ok(orderService.updateOrder(orderRequestDTO, id));
    }

    // Eliminar order
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id){

        orderService.deleteOrder(id);

        return ResponseEntity.noContent().build();
    }
}
