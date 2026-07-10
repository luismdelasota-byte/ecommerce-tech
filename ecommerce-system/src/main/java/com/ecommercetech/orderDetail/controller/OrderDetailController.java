package com.ecommercetech.orderDetail.controller;

import com.ecommercetech.orderDetail.dto.OrderDetailRequestDTO;
import com.ecommercetech.orderDetail.dto.OrderDetailResponseDTO;
import com.ecommercetech.orderDetail.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/orderDetails")
@RequiredArgsConstructor
public class OrderDetailController {

    private OrderDetailService orderDetailService;

    @PostMapping
    public ResponseEntity<OrderDetailResponseDTO> createOrderDetail(@RequestBody OrderDetailRequestDTO orderDetailRequestDTO){
        return ResponseEntity.ok(orderDetailService.createOrderDetail(orderDetailRequestDTO));
    }

    @GetMapping
    public ResponseEntity<List<OrderDetailResponseDTO>> getAllOrderDetails(){
        return ResponseEntity.ok(orderDetailService.getAllOrderDetails());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailResponseDTO> getOrderDetailById(@PathVariable Long id){
        return ResponseEntity.ok(orderDetailService.getOrderDetailById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDetailResponseDTO> updateOrderDetail(@PathVariable Long id, @RequestBody OrderDetailRequestDTO orderDetailRequestDTO){
        return ResponseEntity.ok(orderDetailService.updateOrderDetail(id, orderDetailRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderDetail(@PathVariable Long id) {
        orderDetailService.deleteOrderDetail(id);
        return ResponseEntity.noContent().build();
    }
}
