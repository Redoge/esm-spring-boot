package com.epam.esm.util.mappers.hateoas;

import com.epam.esm.controllers.OrderController;
import com.epam.esm.entities.Order;
import com.epam.esm.pojo.OrderSaveRequestPojo;
import com.epam.esm.util.mappers.hateoas.models.OrderRepresentationModel;
import com.epam.esm.util.mappers.interfaces.HateoasMapperInterface;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@Component
public class OrderHateoasMapper implements HateoasMapperInterface<OrderRepresentationModel, Order> {
    @Override
    public OrderRepresentationModel getRepresentationModel(Order order) {
        return new OrderRepresentationModel(order);
    }

    @Override
    public CollectionModel<OrderRepresentationModel> getCollectionModel(List<Order> orders) throws Exception {
        List<OrderRepresentationModel> orderResources = new ArrayList<>();
        for (var order : orders) {
            orderResources.add(getRepresentationModel(order));
        }
        CollectionModel<OrderRepresentationModel> resources = CollectionModel.of(orderResources);
        resources.add(linkTo(methodOn(OrderController.class).getAll()).withSelfRel());
        resources.add(linkTo(methodOn(OrderController.class).create(new OrderSaveRequestPojo())).withRel("create").withType(HttpMethod.POST.name()));
        return  resources;
    }
}
