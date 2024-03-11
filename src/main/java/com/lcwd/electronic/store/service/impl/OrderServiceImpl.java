package com.lcwd.electronic.store.service.impl;

import com.lcwd.electronic.store.dto.CreateOrderRequest;
import com.lcwd.electronic.store.dto.OrderDto;
import com.lcwd.electronic.store.dto.PageableResponse;
import com.lcwd.electronic.store.dto.UpdateOrderRequest;
import com.lcwd.electronic.store.entities.*;
import com.lcwd.electronic.store.exceptions.BadApiRequest;
import com.lcwd.electronic.store.exceptions.ResourceNotFoundException;
import com.lcwd.electronic.store.helper.Helper;
import com.lcwd.electronic.store.repositories.CartRepository;
import com.lcwd.electronic.store.repositories.OrderRepository;
import com.lcwd.electronic.store.repositories.UserRepository;
import com.lcwd.electronic.store.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OrderServiceImpl implements OrderService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public OrderDto createOrder(CreateOrderRequest request) {
        String userId = request.getUserId();
        String cartId = request.getCartId();

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found!!"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("there is no cart found!!"));

        Set<CartItem> cartItems = cart.getCartItems();
        if (cartItems.size() <= 0) {
            throw new BadApiRequest("there is no item for order!");
        }

        Order order = Order.builder().
                orderId(UUID.randomUUID().toString()).
                orderDate(new Date()).
                orderAmount(cart.getTotalCartPrice()).
                billingAddress(request.getBillingAddress()).
                billingPhone(request.getBillingPhone()).
                billingName(request.getBillingName()).
                deliveredDate(null).
                user(user).
                paymentStatus(request.getPaymentStatus()).
                orderStatus(request.getOrderStatus()).build();

        Set<OrderItem> orderItems = cartItems.stream().map(item -> {
                    OrderItem orderItem = OrderItem.builder()
                            .order(order)
                            .product(item.getProduct())
                            .quantity(item.getQuantity())
                            .totalPrice(item.getTotalPrice())
                            .build();
                    return orderItem;
                }
        ).collect(Collectors.toSet());

        order.setOrderItems(orderItems);
        cart.getCartItems().clear();

        cartRepository.save(cart);
        Order savedOrder = orderRepository.save(order);

        return mapper.map(savedOrder, OrderDto.class);
    }

    @Override
    public void removeOrder(String userId, String orderId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found!!"));
        Set<Order> orders = user.getOrders();

        Order order1 = orders.stream().filter(order -> order.getOrderId() == orderId).findAny().orElseThrow(() -> new ResourceNotFoundException("order not found!!"));
        orders.remove(order1);
        userRepository.save(user);
    }

    @Override
    public Set<OrderDto> getOrderByUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found!!"));
        Set<Order> byUserOrders = orderRepository.findByUser(user);
        Set<OrderDto> orderDtos = byUserOrders.stream().map(order -> {
            OrderDto orderDto = mapper.map(order, OrderDto.class);
            return orderDto;
        }).collect(Collectors.toSet());
        return orderDtos;
    }

    @Override
    public PageableResponse<OrderDto> getAllOrder(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("Desc") ?
                Sort.by(Sort.Direction.DESC, sortBy) :
                Sort.by(Sort.Direction.ASC, sortBy);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Order> all = orderRepository.findAll(pageable);
        PageableResponse<OrderDto> pageableResponse = Helper.getPageableResponse(all, OrderDto.class);
        return pageableResponse;
    }

    @Override
    public OrderDto updateOrder(UpdateOrderRequest request) {
        Order order = orderRepository.findById(request.getOderId()).orElseThrow(() -> new ResourceNotFoundException("order not found!!"));
        order.setBillingAddress(request.getBillingAddress());
        order.setBillingName(request.getBillingName());
        order.setBillingPhone(request.getBillingPhone());
        order.setOrderStatus(request.getOrderStatus());
        order.setPaymentStatus(request.getPaymentStatus());
        Order savedOrder = orderRepository.save(order);
        return mapper.map(savedOrder, OrderDto.class);
    }
}
