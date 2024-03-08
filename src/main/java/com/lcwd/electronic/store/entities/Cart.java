package com.lcwd.electronic.store.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.DatabindException;
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
@Table(name = "carts")
public class Cart {

    @Id
    private String cartId;
    private int totalCartItem;
    private long totalCartPrice;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date lastUpdatedAt;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<CartItem> cartItems = new LinkedHashSet<>();
}
