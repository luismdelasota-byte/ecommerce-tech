package com.ecommercetech.pay.service;

import com.ecommercetech.pay.dto.PayRequestDTO;
import com.ecommercetech.pay.dto.PayResponseDTO;

import java.util.List;

public interface PayService {
    PayResponseDTO createPay(PayRequestDTO payRequestDTO);
    List<PayResponseDTO> getAllPays();
    PayResponseDTO getPayById(Long id);
    PayResponseDTO updatePay( Long id,PayRequestDTO payRequestDTO);
    void deletePay(Long id);
}
