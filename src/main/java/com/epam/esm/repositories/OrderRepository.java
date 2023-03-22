package com.epam.esm.repositories;

import com.epam.esm.entities.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findAllByOwnerId(Long id);
}
