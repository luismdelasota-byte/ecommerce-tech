package com.ecommercetech.user.dto;

import com.ecommercetech.order.dto.OrderResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import com.ecommercetech.order.model.Order;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {

    private Long id;
    private String username;
    private String email;
    private String role;
    private List<OrderResponseDTO> orders = new ArrayList<>();
}


