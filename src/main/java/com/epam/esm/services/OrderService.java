package com.epam.esm.services;

import com.epam.esm.entities.Order;
import com.epam.esm.exceptions.ObjectNotFoundException;
import com.epam.esm.repositories.OrderRepository;
import com.epam.esm.repositories.UserRepository;
import com.epam.esm.services.interfaces.OrderServiceInterface;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements OrderServiceInterface {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> getById(long id) throws ObjectNotFoundException {
        var order = orderRepository.findById(id);
        if(order.isEmpty())
            throw new ObjectNotFoundException("Order", id);
        return order;
    }

    @Override
    public List<Order> getByUserId(long userId) throws ObjectNotFoundException {
        var user = userRepository.findById(userId);
        if(user.isEmpty())
            throw new ObjectNotFoundException("User", userId);
        return user.get().getOrders();
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);//TODO: validate order
    }

    public void deleteById(Long id) throws ObjectNotFoundException {
        if (!orderRepository.existsById(id)) {
            throw new ObjectNotFoundException("Order", id);
        }
        orderRepository.deleteById(id);
    }
}
