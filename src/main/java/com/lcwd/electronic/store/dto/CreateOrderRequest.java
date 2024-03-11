package com.lcwd.electronic.store.dto;

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
    private String userId;
    private String cartId;

    private String orderStatus = "Pending";
    private String paymentStatus = "NotPaid";

    private String billingAddress;
    private String billingPhone;
    private String billingName;

}
