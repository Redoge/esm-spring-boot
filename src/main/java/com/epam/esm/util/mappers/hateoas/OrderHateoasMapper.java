package com.epam.esm.util.mappers.hateoas;

import com.epam.esm.controllers.OrderController;
import com.epam.esm.entities.Order;
import com.epam.esm.pojo.OrderSaveRequestPojo;
import com.epam.esm.util.mappers.hateoas.models.OrderRepresentationModel;
import com.epam.esm.util.mappers.interfaces.HateoasMapperInterface;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

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
    public PagedModel<OrderRepresentationModel> getPagedModel(Page<Order> page, Pageable pageable) throws Exception {
        List<OrderRepresentationModel> tagResources = page.getContent().stream().map(this::getRepresentationModel).toList();
        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(page.getSize(), page.getNumber(),
                page.getTotalElements(), page.getTotalPages());
        PagedModel<OrderRepresentationModel> resources = PagedModel.of(tagResources, metadata);
        resources.add(linkTo(methodOn(OrderController.class).getAll(pageable)).withSelfRel());
        resources.add(linkTo(methodOn(OrderController.class).create(new OrderSaveRequestPojo())).withRel("create").withType(HttpMethod.POST.name()));
        return resources;
    }
}
