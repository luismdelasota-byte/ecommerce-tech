package com.ecommercetech.orderDetail.model;

import com.ecommercetech.product.model.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.ecommercetech.order.model.Order;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_details")
@Builder
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantity")
    private int quantity;


    private double subtotal;

    @Column(name = "unit_price")
    private double unitPrice;

    @ManyToOne
    @JoinColumn(name = "id_order")
    private Order order;

    // Muchos detalles pueden estar asociados al mismo producto
    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product product;


}
