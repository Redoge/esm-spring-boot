package com.epam.esm.services;

import com.epam.esm.entities.Order;
import com.epam.esm.entities.User;
import com.epam.esm.exceptions.ObjectNotFoundException;
import com.epam.esm.repositories.OrderRepository;
import com.epam.esm.repositories.UserRepository;
import com.epam.esm.util.mappers.OrderMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderService orderService;

    private Order order;
    private List<Order> orders;
    private User user;
    private Pageable pageable;
    private Page<Order> pageOrders;

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setId(1L);

        orders = new ArrayList<Order>();
        orders.add(order);

        user = new User();
        user.setId(1L);
        user.setOrders(orders);

        pageable = Pageable.unpaged();
        pageOrders = new PageImpl<>(orders);
    }

    @Test
    void testGetAll() {
        when(orderRepository.findAll()).thenReturn(orders);

        List<Order> result = orderService.getAll();

        assertSame(orders, result);
        verify(orderRepository).findAll();
    }

    @Test
    void testGetAllWithPageable() {
        when(orderRepository.findAll(pageable)).thenReturn(pageOrders);

        Page<Order> result = orderService.getAll(pageable);

        assertSame(pageOrders, result);
        verify(orderRepository).findAll(pageable);
    }

    @Test
    void testGetById() throws ObjectNotFoundException {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Optional<Order> result = orderService.getById(1L);

        assertTrue(result.isPresent());
        assertSame(order, result.get());
        verify(orderRepository).findById(1L);
    }

    @Test
    void testGetByIdNotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> orderService.getById(1L));
        verify(orderRepository).findById(1L);
    }

    @Test
    void testGetByUserId() throws ObjectNotFoundException {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        List<Order> result = orderService.getByUserId(1L);

        assertSame(orders, result);
        verify(userRepository).findById(1L);
    }

    @Test
    void testGetByUserIdWithPageable() throws ObjectNotFoundException {

        when(orderRepository.existsById(1L)).thenReturn(true);
        when(orderRepository.findAllByOwnerId(1L, pageable)).thenReturn(pageOrders);

        Page<Order> result = orderService.getByUserId(1L, pageable);

        assertEquals(pageOrders, result);
    }

    @Test
    void testGetByUserIdNotFound() {
        when(orderRepository.existsById(1L)).thenReturn(false);

        assertThrows(ObjectNotFoundException.class, () -> orderService.getByUserId(1L, pageable));
        verify(orderRepository).existsById(1L);
    }

    @Test
    void testSave() {
        when(orderRepository.save(order)).thenReturn(order);

        Order result = orderService.save(order);

        assertSame(order, result);
        verify(orderRepository).save(order);
    }

    @Test
    void testDelete(){
        when(orderRepository.existsById(1L)).thenReturn(true);
        when(orderRepository.existsById(2L)).thenReturn(false);

        assertThrows(ObjectNotFoundException.class, ()->orderService.deleteById(2L));
        assertDoesNotThrow( ()->orderService.deleteById(1L));


    }
}