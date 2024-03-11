package com.lcwd.electronic.store.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UpdateOrderRequest {
    @NotBlank(message = "order id should not be blank!")
    private String orderId;
    private String orderStatus = "Pending";
    private String paymentStatus = "NotPaid";

    private String billingAddress;
    private String billingPhone;
    private String billingName;
}
