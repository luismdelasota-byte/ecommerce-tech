package com.ecommercetech.orderDetail.service;

import com.ecommercetech.exception.NotFoundException;
import com.ecommercetech.order.repository.OrderRepository;
import com.ecommercetech.orderDetail.dto.OrderDetailRequestDTO;
import com.ecommercetech.orderDetail.dto.OrderDetailResponseDTO;
import com.ecommercetech.orderDetail.mapper.OrderDetailMapper;
import com.ecommercetech.orderDetail.repository.OrderDetailRespository;
import com.ecommercetech.product.model.Product;
import com.ecommercetech.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ecommercetech.orderDetail.model.OrderDetail;

import com.ecommercetech.order.model.Order;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService{

    private final OrderDetailRespository orderDetailRespository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;


    @Override
    public OrderDetailResponseDTO createOrderDetail(OrderDetailRequestDTO orderDetailRequestDTO){

        // Buscamos order
        Order order = orderRepository.findById(orderDetailRequestDTO.getOrderId())
                .orElseThrow(()-> new NotFoundException("Order no encontrada con id " + orderDetailRequestDTO.getOrderId()));

        // Buscamos product
        Product product = productRepository.findById(orderDetailRequestDTO.getProductId())
                .orElseThrow(() -> new NotFoundException("Producto no encontrado con id" + orderDetailRequestDTO.getProductId()));

        // Transformamos a Model
        OrderDetail orderDetail  = OrderDetailMapper.toModel(orderDetailRequestDTO, order, product);

        // Retornamos order como DTO
        return OrderDetailMapper.toResponseDTO(orderDetailRespository.save(orderDetail));
    }

    @Override
    public List<OrderDetailResponseDTO> getAllOrderDetails(){
        return orderDetailRespository.findAll().stream()
                .map(OrderDetailMapper::toResponseDTO)
                .toList();
    }

    @Override
    public OrderDetailResponseDTO getOrderDetailById(Long id){

        OrderDetail orderDetail = orderDetailRespository.findById(id)
                .orElseThrow(()-> new NotFoundException("Detalle no encontrado con id " + id));

        return OrderDetailMapper.toResponseDTO(orderDetail);
    }

    @Override
    public OrderDetailResponseDTO updateOrderDetail(Long id, OrderDetailRequestDTO orderDetailRequestDTO){
        OrderDetail orderDetail = orderDetailRespository.findById(id)
                .orElseThrow(()-> new NotFoundException("Detalle no encontrado con id " + id));

        // Buscamos order por id
        Order order = orderRepository.findById(orderDetailRequestDTO.getOrderId())
                .orElseThrow(()-> new NotFoundException("Order no encontrada con id " + orderDetailRequestDTO.getOrderId()));

        // Buscamos product por id
        Product product = productRepository.findById(orderDetailRequestDTO.getProductId())
                .orElseThrow(() -> new NotFoundException("Producto no encontrado con id" + orderDetailRequestDTO.getProductId()));

        orderDetail.setOrder(order);
        orderDetail.setProduct(product);
        orderDetail.setQuantity(orderDetailRequestDTO.getQuantity());
        orderDetail.setUnitPrice(orderDetailRequestDTO.getUnitPrice());
        orderDetail.setSubtotal(orderDetailRequestDTO.getQuantity() * orderDetailRequestDTO.getUnitPrice());

        return OrderDetailMapper.toResponseDTO(orderDetailRespository.save(orderDetail));
    }

    @Override
    public void deleteOrderDetail(Long id){
        if(!orderDetailRespository.existsById(id)){
            throw new NotFoundException("Detalle no encontrado con id " + id);
        }

        orderDetailRespository.deleteById(id);
    }
}
