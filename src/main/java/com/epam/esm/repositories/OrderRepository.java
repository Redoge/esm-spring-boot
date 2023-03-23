package com.epam.esm.repositories;

import com.epam.esm.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByOwnerId(Long id);
}
