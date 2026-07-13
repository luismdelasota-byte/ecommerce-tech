package com.ecommercetech.pay.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.ecommercetech.pay.dto.PayRequestDTO;
import com.ecommercetech.pay.dto.PayResponseDTO;

import com.ecommercetech.pay.service.PayService;

import java.util.List;

@RestController
@RequestMapping("/api/pay")
@RequiredArgsConstructor
public class PayController {

    private final PayService payService;

    @PostMapping
    public ResponseEntity<PayResponseDTO> createPay(@RequestBody PayRequestDTO payRequestDTO) {
        return ResponseEntity.ok(payService.createPay(payRequestDTO));
    }

    @GetMapping
    public ResponseEntity<List<PayResponseDTO>> getAllPays() {
        return ResponseEntity.ok(payService.getAllPays());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PayResponseDTO> getPayById(@PathVariable Long id) {
        return ResponseEntity.ok(payService.getPayById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PayResponseDTO> updatePay(@PathVariable Long id, @RequestBody PayRequestDTO payRequestDTO) {
        return ResponseEntity.ok(payService.updatePay(id, payRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePay(@PathVariable Long id) {
        payService.deletePay(id);
        return ResponseEntity.noContent().build();
    }
}
