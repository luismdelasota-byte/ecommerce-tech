package com.ecommercetech.pay.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayResponseDTO {
    private Long id;
    private Long orderId;
    private String paymentMethod;
    private String state;
}
