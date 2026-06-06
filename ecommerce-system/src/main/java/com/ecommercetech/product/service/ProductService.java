package com.ecommercetech.product.service;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //Getter y Setter
@Entity
@AllArgsConstructor //Constructor con parametros
@NoArgsConstructor  //Constructor sin parametros
public class ProductService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String category;
    private double price;
    private int stock;
}
