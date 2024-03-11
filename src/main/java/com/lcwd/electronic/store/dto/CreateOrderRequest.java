package com.lcwd.electronic.store.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CreateOrderRequest {
    @NotBlank(message = "userId should not be blank")
    private String userId;
    @NotBlank(message = "cartId should not be blank")
    private String cartId;

    private String orderStatus = "Pending";
    private String paymentStatus = "NotPaid";

    @NotBlank(message = "address should not be blank")
    private String billingAddress;
    @NotBlank(message = "phone number should not be blank")
    private String billingPhone;
    @NotBlank(message = "name should not be blank")
    private String billingName;

}
