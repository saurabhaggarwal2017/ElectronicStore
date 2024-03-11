package com.lcwd.electronic.store.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderItemId;
    private int quantity;
    private long totalPrice;

    @ManyToOne
    @JoinColumn(name = "product_Id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_Id")
    private Order order;


}
