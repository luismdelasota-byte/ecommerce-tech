package com.ecommercetech;

import com.ecommercetech.order.model.Order;
import com.ecommercetech.order.dto.OrderRequestDTO;
import com.ecommercetech.order.dto.OrderResponseDTO;
import com.ecommercetech.order.repository.OrderRepository;
import com.ecommercetech.order.service.OrderServiceImpl;
import com.ecommercetech.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.ecommercetech.user.model.User;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Optional;
import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private OrderServiceImpl orderServiceImpl;

    @Test
    void testCreateOrder(){
        // Arrange (datos)
        OrderRequestDTO orderRequestDTO = OrderRequestDTO.builder()
                .date(LocalDate.now())
                .state("pending")
                .userId(1L)
                .build();

        // Buscamos el userId en el model User
        User user = User.builder()
                .id(orderRequestDTO.getUserId())
                .build();

        // Simulamos buscar el user en el repositorio
        Mockito.when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        Order order = Order.builder()
                .id(1L)
                .date(orderRequestDTO.getDate())
                .state(orderRequestDTO.getState())
                .user(user)
                .details(new ArrayList<>()) // Creamos lista vacia, ya que el model Order, presenta una lista de detalles
                .build();

        // simulacion del repositorio
        Mockito.when(orderRepository.save(Mockito.any(Order.class)))
                .thenReturn(order);

        // Act(ejecutamos simulacion por service)
        OrderResponseDTO result = orderServiceImpl.createOrder(orderRequestDTO);

        // Assert (verificamos resultados)
        assertEquals(1L,result.getId());
        assertEquals("pending", result.getState());
        assertEquals(orderRequestDTO.getDate(),result.getDate());
        assertEquals(1L, result.getUserId());

        // Opcional verificamos que el metodo de los mock fue llamado
        Mockito.verify(userRepository).findById(1L);
        Mockito.verify(orderRepository).save(Mockito.any(Order.class));

    }
}
