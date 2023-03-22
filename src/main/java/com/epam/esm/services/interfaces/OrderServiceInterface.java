package com.epam.esm.services.interfaces;


import com.epam.esm.entities.Order;

import java.util.List;
import java.util.Optional;

public interface OrderServiceInterface {
    List<Order> getAll();
    Optional<Order> getById();
    List<Order> getByUserId(long userId);
    Order save(Order order);
}
