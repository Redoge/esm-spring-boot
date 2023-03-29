package com.epam.esm.services.interfaces;


import com.epam.esm.entities.Order;
import com.epam.esm.exceptions.BadRequestException;
import com.epam.esm.exceptions.ObjectNotFoundException;
import com.epam.esm.pojo.OrderSaveRequestPojo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OrderServiceInterface {
    List<Order> getAll();
    Page<Order> getAll(Pageable pageable);
    Optional<Order> getById(long id) throws ObjectNotFoundException;
    List<Order> getByUserId(long userId) throws ObjectNotFoundException;
    Order save(Order order);
    Order saveByPojo(OrderSaveRequestPojo order) throws ObjectNotFoundException, BadRequestException;
    void deleteById(Long id) throws ObjectNotFoundException;

}
