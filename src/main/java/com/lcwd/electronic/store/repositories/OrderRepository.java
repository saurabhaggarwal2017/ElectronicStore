package com.lcwd.electronic.store.repositories;

import com.lcwd.electronic.store.entities.Order;
import com.lcwd.electronic.store.entities.OrderItem;
import com.lcwd.electronic.store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

public interface OrderRepository extends JpaRepository<Order, String> {
    Set<Order> findByUser(User user);
}
