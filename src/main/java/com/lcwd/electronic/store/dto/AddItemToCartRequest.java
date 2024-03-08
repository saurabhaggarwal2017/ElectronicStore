package com.lcwd.electronic.store.dto;

import com.lcwd.electronic.store.entities.Product;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AddItemToCartRequest {

    @Min(value = 1, message = "Quantity should be more then 0!")
    @Max(value = 10, message = "Quantity should not be more then 10!")
    private int quantity;
    private String productId;
}
