package com.epam.esm.services;

import com.epam.esm.entities.Order;
import com.epam.esm.exceptions.BadRequestException;
import com.epam.esm.exceptions.ObjectNotFoundException;
import com.epam.esm.pojo.OrderSaveRequestPojo;
import com.epam.esm.repositories.OrderRepository;
import com.epam.esm.repositories.UserRepository;
import com.epam.esm.services.interfaces.OrderServiceInterface;
import com.epam.esm.util.mappers.OrderMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class OrderService implements OrderServiceInterface {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }
    @Override
    public Page<Order> getAll(Pageable pageable) {
        return orderRepository.findAll(pageable);
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
    public Page<Order> getByUserId(long userId, Pageable pageable) throws ObjectNotFoundException {
        if(!orderRepository.existsById(userId))
            throw new ObjectNotFoundException("User", userId);
        return orderRepository.findAllByOwnerId(userId, pageable);
    }
    @Override
    public Order save(Order order) {
        return orderRepository.save(order);//TODO: validate order
    }

    @Override
    public Order saveByPojo(OrderSaveRequestPojo orderPojo) throws ObjectNotFoundException, BadRequestException {
        if(isEmpty(orderPojo.getUserId()) || isEmpty(orderPojo.getCertId()))
            throw new BadRequestException("userId and certId must be not null");
        var user = userRepository.findById(orderPojo.getUserId());
        if(user.isPresent()){
            var gCertsIsReceived = user.get()
                    .getOrders()
                    .stream()
                    .map(Order::getId)
                    .toList().contains(orderPojo.getCertId());
            if(gCertsIsReceived)
                throw new BadRequestException("The certificate is already yours");
        }
        var order = orderMapper.mapPojoToOrder(orderPojo);
        return save(order);
    }

    @Override
    public void deleteById(Long id) throws ObjectNotFoundException {
        if (!orderRepository.existsById(id)) {
            throw new ObjectNotFoundException("Order", id);
        }
        orderRepository.deleteById(id);
    }
}
