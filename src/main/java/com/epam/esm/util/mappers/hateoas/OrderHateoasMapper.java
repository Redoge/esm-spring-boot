package com.epam.esm.util.mappers.hateoas;

import com.epam.esm.controllers.OrderController;
import com.epam.esm.controllers.TagController;
import com.epam.esm.entities.Order;
import com.epam.esm.util.mappers.interfaces.HateoasMapperInterface;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@Component
public class OrderHateoasMapper implements HateoasMapperInterface<Order> {
    @Override
    public EntityModel<Order> getEntityModel(Order tag) throws Exception {
        EntityModel<Order> orderResource = EntityModel.of(tag);
        orderResource.add(linkTo(methodOn(OrderController.class).getById(tag.getId())).withSelfRel());
        orderResource.add(linkTo(methodOn(OrderController.class).removeById(tag.getId())).withRel("delete").withType(HttpMethod.DELETE.name()));
        return orderResource;
    }

    @Override
    public CollectionModel<EntityModel<Order>> getCollectionModel(List<Order> orders) throws Exception {
        List<EntityModel<Order>> orderResources = new ArrayList<>();
        for (var order : orders) {
            orderResources.add(getEntityModel(order));
        }
        CollectionModel<EntityModel<Order>> resources = CollectionModel.of(orderResources);
        resources.add(linkTo(methodOn(OrderController.class).getAll()).withSelfRel());
        resources.add(linkTo(methodOn(OrderController.class).create(null)).withRel("create").withType(HttpMethod.POST.name()));
        return  resources;
    }
}
