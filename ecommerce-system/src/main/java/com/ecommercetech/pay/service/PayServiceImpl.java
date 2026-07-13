package com.ecommercetech.pay.service;

import com.ecommercetech.pay.dto.*;
import com.ecommercetech.pay.mapper.PayMapper;
import com.ecommercetech.pay.model.Pay;
import com.ecommercetech.pay.repository.PayRepository;
import com.ecommercetech.order.model.Order;
import com.ecommercetech.order.repository.OrderRepository;
import com.ecommercetech.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PayServiceImpl implements PayService {

    private final PayRepository payRepository;
    private final OrderRepository orderRepository;

    @Override
    public PayResponseDTO createPay(PayRequestDTO dto) {
        Order order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new NotFoundException("Orden no encontrada con id " + dto.getOrderId()));

        Pay pay = PayMapper.toModel(dto, order);
        return PayMapper.toResponseDTO(payRepository.save(pay));
    }

    @Override
    public List<PayResponseDTO> getAllPays() {
        return payRepository.findAll()
                .stream()
                .map(PayMapper::toResponseDTO)
                .toList();
    }

    @Override
    public PayResponseDTO getPayById(Long id) {
        Pay pay = payRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pago no encontrado con id " + id));
        return PayMapper.toResponseDTO(pay);
    }

    @Override
    public PayResponseDTO updatePay(Long id, PayRequestDTO dto) {
        Pay pay = payRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pago no encontrado con id " + id));

        Order order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new NotFoundException("Orden no encontrada con id " + dto.getOrderId()));

        pay.setOrder(order);
        pay.setPaymentMethod(dto.getPaymentMethod());
        pay.setState(dto.getState());

        return PayMapper.toResponseDTO(payRepository.save(pay));
    }

    @Override
    public void deletePay(Long id) {
        if (!payRepository.existsById(id)) {
            throw new NotFoundException("Pago no encontrado con id " + id);
        }
        payRepository.deleteById(id);
    }
}
