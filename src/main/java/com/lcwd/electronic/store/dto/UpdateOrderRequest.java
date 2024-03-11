package com.lcwd.electronic.store.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UpdateOrderRequest {
    private String oderId;
    private String orderStatus = "Pending";
    private String paymentStatus = "NotPaid";

    private String billingAddress;
    private String billingPhone;
    private String billingName;
}
