package com.lcwd.electronic.store.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {
    @Id
    private String orderId;
    private String orderStatus;
    private String paymentStatus;
    private long orderAmount;
    @Column(length = 1000)
    private String billingAddress;
    private String billingPhone;
    private String billingName;
    private Date orderDate;
    private Date deliveredDate;

    // relationship

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    Set<OrderItem> orderItems = new LinkedHashSet<>();



}
