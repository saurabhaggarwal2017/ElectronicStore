package com.lcwd.electronic.store.controllers;

import com.lcwd.electronic.store.dto.*;
import com.lcwd.electronic.store.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody CreateOrderRequest orderRequest) {
        OrderDto order = orderService.createOrder(orderRequest);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @DeleteMapping("/{orderId}/user/{userId}")
    public ResponseEntity<ApiResponseMessage> removeOrder(@PathVariable String userId, @PathVariable String orderId) {
        orderService.removeOrder(userId, orderId);
        ApiResponseMessage orderDeletedSuccessfully = ApiResponseMessage.builder().success(true).message("order deleted successfully").build();
        return new ResponseEntity<>(orderDeletedSuccessfully, HttpStatus.OK);

    }

    @GetMapping("user/{userId}")
    public ResponseEntity<Set<OrderDto>> getUserOrders(@PathVariable String userId) {
        Set<OrderDto> orderByUser = orderService.getOrderByUser(userId);
        return new ResponseEntity<>(orderByUser, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PageableResponse<OrderDto>> getAllOrders(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "orderDate") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir

    ) {
        PageableResponse<OrderDto> allOrder = orderService.getAllOrder(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(allOrder, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<OrderDto> updateOrder(@Valid @RequestBody UpdateOrderRequest request) {
        OrderDto orderDto = orderService.updateOrder(request);
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }

}
