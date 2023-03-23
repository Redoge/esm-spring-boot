package com.epam.esm.services;

import com.epam.esm.entities.Order;
import com.epam.esm.repositories.OrderRepository;
import com.epam.esm.repositories.UserRepository;
import com.epam.esm.services.interfaces.OrderServiceInterface;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public Optional<Order> getById(long id) {
        return orderRepository.findById(id); //TODO: check is exist
    }

    @Override
    public List<Order> getByUserId(long userId) {
        var user = userRepository.findById(userId);
        if(user.isPresent()){
            return user.get().getOrders();
        }
        return new ArrayList<>();
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);//TODO: validate order
    }

    public void deleteById(Long id){
        orderRepository.deleteById(id);//TODO: check is exists
    }
}
