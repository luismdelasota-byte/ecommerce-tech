package com.ecommercetech.pay.model;

import jakarta.persistence.*;
import lombok.*;
import com.ecommercetech.order.model.Order;

@Entity
@Table(name = "pay")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con Order
    @ManyToOne
    @JoinColumn(name = "id_order", nullable = false)
    private Order order;

    @Column(name = "payment_method", nullable = false, length = 50)
    private String paymentMethod;

    @Column(name = "state", nullable = false)
    private String state = "pending"; // valor por defecto
}
