package com.lcwd.electronic.store.dto;

import com.lcwd.electronic.store.entities.CartItem;
import com.lcwd.electronic.store.entities.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDto {

    private String cartId;
    private int totalCartItem;
    private long totalCartPrice;
    private Date lastUpdatedAt;
    private UserDto user;

    private Set<CartItemDto> cartItems;
}
