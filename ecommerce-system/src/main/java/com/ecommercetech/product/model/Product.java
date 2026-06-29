package com.ecommercetech.product.model;
import com.ecommercetech.orderDetail.model.OrderDetail;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data //Getter y Setter
@Entity
@AllArgsConstructor //Constructor con parametros
@NoArgsConstructor  //Constructor sin parametros
@Builder
@Table(name = "product") // // Le dice a JPA/Hibernate qué tabla real de la base de datos corresponde a esa entidad.
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String category;
    private double price;
    private int stock;


    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> details = new ArrayList<>();
}
