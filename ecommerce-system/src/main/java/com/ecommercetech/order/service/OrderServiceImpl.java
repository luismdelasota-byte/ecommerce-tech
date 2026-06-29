package com.ecommercetech.order.service;

import com.ecommercetech.exception.NotFoundException;
import com.ecommercetech.order.dto.OrderRequestDTO;
import com.ecommercetech.order.dto.OrderResponseDTO;
import com.ecommercetech.order.mapper.OrderMapper;
import com.ecommercetech.order.model.Order;
import com.ecommercetech.order.repository.OrderRepository;
import com.ecommercetech.user.dto.UserRequestDTO;
import com.ecommercetech.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

import com.ecommercetech.user.model.User;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    // Crear order
    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO){

        User user = userRepository.findById(orderRequestDTO.getUserId())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con id " + orderRequestDTO.getUserId()));
        Order order = OrderMapper.toModel(orderRequestDTO, user);

        /*
        Order order = Order.builder()
                .id(orderRequestDTO.getId()) //No es necesario setear el id, ya que se genera automaticamente
                .date(orderRequestDTO.getDate())
                .state(orderRequestDTO.getState())
                .user(orderRequestDTO.getUser())
                .build();
         */
        return OrderMapper.toResponseDTO(orderRepository.save(order));
    }

    // Listar orders
    @Override
    public List<OrderResponseDTO> getAllOrders(){
        return orderRepository.findAll()
                .stream()
                .map(OrderMapper::toResponseDTO)
                .toList();
    }

    // Buscas por id
    @Override
    public OrderResponseDTO getOrderById(Long id){
        Order order = orderRepository.findById(id).
                orElseThrow(() -> new NotFoundException("Orden no encontrada con id " + id));

        return OrderMapper.toResponseDTO(order);
    }

    // Modificar order
    @Override
    public OrderResponseDTO updateOrder(OrderRequestDTO orderRequestDTO, Long id){
        Order order = orderRepository.findById(id).
                orElseThrow(() -> new NotFoundException("Orden no encontrada con id " + id));

        // Buscar el User por su id
        User user = userRepository.findById(orderRequestDTO.getUserId())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con id " + orderRequestDTO.getUserId()));

        order.setDate(orderRequestDTO.getDate());
        order.setState(orderRequestDTO.getState());
        order.setUser(user);

        return OrderMapper.toResponseDTO(orderRepository.save(order));
    }

    // Eliminar order
    public void deleteOrder(Long id){
        if(!orderRepository.existsById(id)){
            throw new NotFoundException("Orden no encontrada con id " + id);
        }

        orderRepository.deleteById(id);
    }


}
