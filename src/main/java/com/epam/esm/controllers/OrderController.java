package com.epam.esm.controllers;

import com.epam.esm.entities.Order;
import com.epam.esm.exceptions.BadRequestException;
import com.epam.esm.exceptions.ObjectNotFoundException;
import com.epam.esm.pojo.OrderSaveRequestPojo;
import com.epam.esm.services.OrderService;
import com.epam.esm.util.mappers.hateoas.models.OrderRepresentationModel;
import com.epam.esm.util.mappers.interfaces.HateoasMapperInterface;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.epam.esm.util.StringConst.deletedSuccessfully;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final HateoasMapperInterface<OrderRepresentationModel, Order> hateoasMapper;

    @GetMapping
    public PagedModel<OrderRepresentationModel> getAll(Pageable pageable) throws Exception {
        var orders = orderService.getAll(pageable);
        return hateoasMapper.getPagedModel(orders, pageable);
    }
    @GetMapping("/{id}")
    public OrderRepresentationModel getById(@PathVariable long id) throws Exception {
        var order = orderService.getById(id);
        return hateoasMapper.getRepresentationModel(order.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeById(@PathVariable long id) throws ObjectNotFoundException {
        orderService.deleteById(id);
        return ResponseEntity.ok(deletedSuccessfully);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Order> create(@RequestBody OrderSaveRequestPojo orderPojo) throws ObjectNotFoundException, BadRequestException {
        var order = orderService.saveByPojo(orderPojo);
        return ResponseEntity.ok(order);
    }
}
