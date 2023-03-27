package com.epam.esm.util.mappers.hateoas.models;

import com.epam.esm.controllers.GiftCertificateController;
import com.epam.esm.controllers.OrderController;
import com.epam.esm.controllers.UserController;
import com.epam.esm.entities.Order;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderRepresentationModel extends RepresentationModel<OrderRepresentationModel> {
    private final long id;

    private final LocalDateTime purchaseTime;

    private final BigDecimal price;

    public OrderRepresentationModel(Order order){
        this.id = order.getId();
        this.price = order.getPrice();
        this.purchaseTime = order.getPurchaseTime();

        try {
            add(linkTo(methodOn(OrderController.class).getById(order.getId())).withSelfRel());
            add(linkTo(methodOn(OrderController.class).removeById(order.getId())).withRel("delete").withType("DELETE"));
            add(linkTo(methodOn(UserController.class).getById(order.getOwner().getId())).withRel("owner"));
            add(linkTo(methodOn(GiftCertificateController.class).getById(order.getGiftCertificate().getId())).withRel("gift_certificate"));
        } catch (Exception ignored) {
        }
    }

}
