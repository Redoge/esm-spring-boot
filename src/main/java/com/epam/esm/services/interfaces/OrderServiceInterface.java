package com.epam.esm.services.interfaces;


import com.epam.esm.entities.Order;
import com.epam.esm.exceptions.ObjectNotFoundException;

import java.util.List;
import java.util.Optional;

public interface OrderServiceInterface {
    List<Order> getAll();
    Optional<Order> getById(long id) throws ObjectNotFoundException;
    List<Order> getByUserId(long userId) throws ObjectNotFoundException;
    Order save(Order order);
}
