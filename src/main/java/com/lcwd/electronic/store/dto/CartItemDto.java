package com.lcwd.electronic.store.dto;

import com.lcwd.electronic.store.entities.Cart;
import com.lcwd.electronic.store.entities.Product;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDto {

    private int cardItemId;
    private int quantity;
    private long totalPrice;
    private ProductDto product;
}
