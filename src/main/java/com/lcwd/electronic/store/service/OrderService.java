package com.lcwd.electronic.store.service;

import com.lcwd.electronic.store.dto.CreateOrderRequest;
import com.lcwd.electronic.store.dto.OrderDto;
import com.lcwd.electronic.store.dto.PageableResponse;
import com.lcwd.electronic.store.dto.UpdateOrderRequest;

import java.util.Set;

public interface OrderService {
    //create order
    OrderDto createOrder(CreateOrderRequest request);

    // remove order
    void removeOrder(String userId, String orderId);

    // get orders by user
    Set<OrderDto> getOrderByUser(String userId);

    // get all orders
    PageableResponse<OrderDto> getAllOrder(int pageNumber, int pageSize, String sortBy, String sortDir);

    //update
    OrderDto updateOrder(UpdateOrderRequest request);
}
