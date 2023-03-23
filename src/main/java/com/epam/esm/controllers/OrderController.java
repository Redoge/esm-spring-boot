package com.epam.esm.controllers;

import com.epam.esm.entities.Order;
import com.epam.esm.services.OrderService;
import com.epam.esm.util.mappers.interfaces.HateoasMapperInterface;
import jakarta.transaction.Transactional;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class OrderController {
    private final OrderService orderService;
    private final HateoasMapperInterface<Order> hateoasMapper;

    public OrderController(OrderService orderService, HateoasMapperInterface<Order> hateoasMapper) {
        this.orderService = orderService;
        this.hateoasMapper = hateoasMapper;
    }
    @GetMapping
    public CollectionModel<EntityModel<Order>> getAll() throws Exception {
        var orders = orderService.getAll();
        return hateoasMapper.getCollectionModel(orders);
    }
    @GetMapping("/{id}")
    public EntityModel<Order> getById(@PathVariable long id) throws Exception {
        var order = orderService.getById(id);
        return hateoasMapper.getEntityModel(order.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeById(@PathVariable long id){
        orderService.deleteById(id);
        return ResponseEntity.ok("Deleted successfully!");
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Order> create(@RequestBody Order user)  {
        var order = orderService.save(user);
        return ResponseEntity.ok(order);
    }
}
