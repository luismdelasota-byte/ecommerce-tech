package com.ecommercetech.order.model;

import jakarta.persistence.*;

import com.ecommercetech.orderDetail.model.OrderDetail;
import com.ecommercetech.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private String state;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> details = new ArrayList<>();
    /*
    mappedBy = "order" no es una columna, sino el nombre del atributo en al clase OrderDetail
    que apunta a Order.

    Le estamos diciendo a JPA: "la relacion ya esta definida en el lado de OrderDetail, usa la columna
    (id_order) para enlazarla.

    cascade = CascadeType.ALL -> Significa que las operaciones sobre Order(guarda, actualiza, eliminar)
    se propagan automaticamente a sus OrderDetail.
    Ejm: Si eliminamos una Order, tambien se eliminan sus OrderDetail.

    orphanRemoval = true -> Si quitamos un OrderDetail de la lista details, JPA lo elimina de la base
    de datos. Ejm: order.getDetails().remove(detallX); -> ese detallX se borra de la tabla order_details

    En general private List<OrderDetail> details = new ArrayList<>(); es simplemente una coleccion
    en Java que contendra los detalles de la orden, no se convierte en una columna en esta tabla,
    es decir colecciones o relaciones(List<OrderDetal>, Set<Product>, etc) no son columnas, solo
    representan relaciones entre tablas
    */

    /* Flujo:

    * Usuario (cliente)
       |
Usuario → Order

El cliente hace una compra.

Se crea un registro en la tabla orders con el ID de usuario, fecha, estado, total.
       |
   Order (cabecera del pedido)
       |
Cada producto comprado se guarda como un detalle.

En la tabla order_details, cada fila tiene id_order apuntando al pedido.

Ejemplo: si el pedido tiene 3 productos, habrá 3 registros en order_details con el mismo id_order.
       |
   OrderDetail (líneas del pedido)
       |
Cada detalle está vinculado a un producto específico mediante id_product.

Así sabes qué producto fue comprado, su precio unitario y cantidad.
       |
   Product (producto específico)
*/
}
